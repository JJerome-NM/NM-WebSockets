package com.jjerome.filters;

import com.jjerome.context.annotation.HasRole;

import java.lang.annotation.Annotation;

public class SecuretyFiltersFactory {

    public HasAnyRoleFilter build(HasRole annotation){
        return new HasAnyRoleFilter(annotation, new Annotation[]{});
    }

//    public PreAuthorizeFilter build(PreAuthorize annotation){
//        return new PreAuthorizeFilter(annotation, new Annotation[]{});
//    }
}
