package com.jjerome.util;

import com.jjerome.domain.InitialClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterUtil {

    private final InitialClass initialClass;

    public void doUtil(){
        System.out.println(initialClass.getClazz());
    }
}
