package com.jjerome.domain;

import java.util.Objects;

public interface ApplicationFilterChain extends FilterChain {

    void doConnectFilter();

    static void doConnectFilterIfNotNull(ApplicationFilterChain filterChain) {
        if (!Objects.isNull(filterChain)){
            filterChain.doConnectFilter();
        }
    }

    static void doFilterIfNotNull(ApplicationFilterChain filterChain) {
        if (!Objects.isNull(filterChain)){
            filterChain.doFilter();
        }
    }

    static void getOrderIfNotNull(ApplicationFilterChain filterChain) {
        if (!Objects.isNull(filterChain)){
            filterChain.getOrder();
        }
    }

//    abstract class VirtualApplicationFilterChain{
//
//        public static void doConnectFilterIfNotNull(ApplicationFilterChain filterChain) {
//            if (!Objects.isNull(filterChain)){
//                filterChain.doConnectFilter();
//            }
//        }
//
//        public static void doFilterIfNotNull(ApplicationFilterChain filterChain) {
//            if (!Objects.isNull(filterChain)){
//                filterChain.doFilter();
//            }
//        }
//
//        public static void getOrderIfNotNull(ApplicationFilterChain filterChain) {
//            if (!Objects.isNull(filterChain)){
//                filterChain.getOrder();
//            }
//        }
//    }
}
