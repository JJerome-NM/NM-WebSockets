package com.jjerome_test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Data
@Builder
public class Good<T, R> {

    private boolean good;

    private T body;

    private R r;
}
