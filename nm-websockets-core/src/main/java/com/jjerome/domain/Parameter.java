package com.jjerome.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Parameter {

    private Class<?> clazz;

    private Parameter[] generics;
    public Parameter(Class<?> clazz){
        this.clazz = clazz;
        this.generics = null;
    }
}
