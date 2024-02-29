package com.jjerome.reflection.context;

import com.jjerome.core.Request;
import com.jjerome.reflection.context.annotation.WSAllQueryParams;
import com.jjerome.reflection.context.annotation.WSPathVariable;
import com.jjerome.reflection.context.annotation.WSQueryParam;
import com.jjerome.reflection.context.annotation.WSRequestBody;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class MethodParameterFactory {

    private static final Map<Class<? extends Annotation>, ParameterType> ANNOTATION_2_PARAMETER_TYPE = Map.ofEntries(
            Pair.of(WSPathVariable.class, ParameterType.PATH_VARIABLE),
            Pair.of(WSRequestBody.class, ParameterType.REQUEST_BODY),
            Pair.of(WSQueryParam.class, ParameterType.QUERY_PARAM),
            Pair.of(WSAllQueryParams.class, ParameterType.ALL_QUERY_PARAM)
    );
    private static final Map<Class<?>, ParameterType> PARAMETER_CLASS_TYPE_2_PARAMETER_TYPE = Map.ofEntries(
            Pair.of(Request.class, ParameterType.REQUEST)
    );
    private static final Set<Class<? extends Annotation>> AVAILABLE_ANNOTATIONS = new HashSet<>(ANNOTATION_2_PARAMETER_TYPE.keySet());
    private static final Set<Class<?>> AVAILABLE_PARAMETER_CLASSES = new HashSet<>(PARAMETER_CLASS_TYPE_2_PARAMETER_TYPE.keySet());

    MethodParameterFactory() {
    }

    public AnnotatedParameter buildAnnotatedParameter(String name, Annotation[] annotations,
                                                      Class<?> clazz, MethodParameter[] generics) {
        Class<? extends Annotation> annotation = getAvailableAnnotation(annotations);
        ParameterType parameterType = Optional.ofNullable(annotation)
                .map(ANNOTATION_2_PARAMETER_TYPE::get)
                .orElseGet(() -> ObjectUtils.defaultIfNull(PARAMETER_CLASS_TYPE_2_PARAMETER_TYPE.get(clazz), ParameterType.UNDEFINED_DATA));
        return new AnnotatedParameter(name, annotations, clazz, generics, parameterType);
    }

    public AnnotatedParameter buildAnnotatedParameter(String name, Annotation[] annotations, Class<?> clazz) {
        return buildAnnotatedParameter(name, annotations, clazz, new MethodParameter[]{});
    }

    private Class<? extends Annotation> getAvailableAnnotation(Annotation[] annotations) {
        Set<Class<? extends Annotation>> allowedAnnotations = new HashSet<>();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (AVAILABLE_ANNOTATIONS.contains(annotationType)) {
                if (!allowedAnnotations.isEmpty()) {
                    throw new RuntimeException("Parameter can contain only one allowed mapping annotation");
                }
                allowedAnnotations.add(annotationType);
            }
        }
        return !allowedAnnotations.isEmpty() ? allowedAnnotations.iterator().next() : null;
    }
}
