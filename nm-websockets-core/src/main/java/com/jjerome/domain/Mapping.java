package com.jjerome.domain;

import com.jjerome.annotation.WSMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Mapping {

    private WSMapping mappingAnnotation;

    private Method method;

    private MethodParameters[] methodParams;

    private Parameter methodReturnType;
}
