package com.jjerome.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;

@AllArgsConstructor
@Getter @Setter
public class Controller {

    Annotation[] annotations;

    Class<?> clazz;

    Object springBean;
}
