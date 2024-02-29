package com.jjerome.reflection.context;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Stream;

public class MethodParameter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Class<?> clazz;

    private final MethodParameter[] generics;

    private final JavaType type;

    private final String name;

    public MethodParameter(String name, Class<?> clazz, MethodParameter[] generics) {
        this.name = name;
        this.clazz = clazz;
        this.generics = generics;

        this.type = this.converToJavaType();
    }

    public MethodParameter(String name, Class<?> clazz) {
        this(name, clazz, new MethodParameter[]{});
    }

    public boolean hasGenerics() {
        return generics != null;
    }

    public JavaType converToJavaType() {
        if (!hasGenerics()) {
            return OBJECT_MAPPER.getTypeFactory().constructType(clazz);
        }
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(clazz, convertGenericsToJavaType());
    }

    public JavaType[] convertGenericsToJavaType() {
        if (!hasGenerics()) {
            return new JavaType[]{};
        }
        return Stream.of(generics)
                .map(generic -> OBJECT_MAPPER.getTypeFactory()
                        .constructParametricType(generic.getClazz(), generic.convertGenericsToJavaType()))
                .toArray(JavaType[]::new);
    }

    public JavaType getType() {
        return this.type;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public MethodParameter[] getGenerics() {
        return generics;
    }

    public String getName() {
        return name;
    }
}
