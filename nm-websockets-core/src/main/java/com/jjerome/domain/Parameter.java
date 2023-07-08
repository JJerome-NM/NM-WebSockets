package com.jjerome.domain;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Parameter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Class<?> clazz;

    private Parameter[] generics;
    public Parameter(Class<?> clazz){
        this.clazz = clazz;
        this.generics = null;
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

        short genericsLength = (short) generics.length;
        JavaType[] genericsTypes = new JavaType[genericsLength];

        for (int i = 0; i < genericsLength; i++){

            genericsTypes[i] = OBJECT_MAPPER.getTypeFactory()
                    .constructParametricType(generics[i].getClazz(), generics[i].convertGenericsToJavaType());
        }

        return genericsTypes;
    }
}
