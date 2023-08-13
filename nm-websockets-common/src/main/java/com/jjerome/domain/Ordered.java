package com.jjerome.domain;

public interface Ordered {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int APPLICATION_PRECEDENCE = 1000;


    int getOrder();
}
