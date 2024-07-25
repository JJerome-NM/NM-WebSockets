package com.jjerome.sync.reflection.context.annotation;

import com.jjerome.sync.reflection.context.enums.GetterStyle;
import com.jjerome.sync.reflection.context.enums.SetterStyle;

public @interface SyncMe {

    GetterStyle getterStyle() default GetterStyle.DEFAULT;

    SetterStyle setterStyle() default SetterStyle.DEFAULT;
}
