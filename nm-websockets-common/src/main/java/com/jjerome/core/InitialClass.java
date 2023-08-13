package com.jjerome.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Annotation;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class InitialClass {

    Annotation[] annotations;

    Class<?> clazz;

    Object springBean;
}
