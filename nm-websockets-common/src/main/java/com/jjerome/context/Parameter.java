package com.jjerome.context;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Stream;

@Getter @Setter
public class Parameter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Class<?> clazz;

    private Parameter[] generics;

    private final JavaType type;

    public Parameter(Class<?> clazz, Parameter[] generics){
        this.clazz = clazz;
        this.generics = generics;

        this.type = this.converToJavaType();
    }

    public Parameter(Class<?> clazz){
        this(clazz, null);
    }

    public Parameter(){
        this(null, null);
    }

    public boolean hasGenerics(){
        return generics != null;
    }

    public JavaType converToJavaType(){
        if (!hasGenerics()){
            return OBJECT_MAPPER.getTypeFactory().constructType(clazz);
        }
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(clazz, convertGenericsToJavaType());
    }

    public JavaType[] convertGenericsToJavaType(){
        if (!hasGenerics()){
            return new JavaType[] {};
        }
        return Stream.of(generics)
                .map(generic -> OBJECT_MAPPER.getTypeFactory()
                        .constructParametricType(generic.getClazz(), generic.convertGenericsToJavaType()))
                .toArray(JavaType[]::new);
    }

    public JavaType getType(){
        return this.type;
    }
}
