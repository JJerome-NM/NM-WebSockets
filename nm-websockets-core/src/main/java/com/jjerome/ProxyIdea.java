//package com.jjerome;
//
//
//interface Mapping {
//
//    public boolean someMethod();
//
//    public boolean otherMethod();
//}
//
////@Proxy
//public class ProxyIdea implements Mapping {
//
//    //  Можна додати анотацію @ProxyInstance
//    private final Mapping mapping;
//
////  Або при інснуючому методі
////    @Override
////    public boolean someMethod() {
////        return true;
////        Після поверенення викликати mapping.someMethod();
////        Або додати анотацію яка буде щось таке робити
////    }
//
//
//    @Override
//    public boolean someMethod() {
//        mapping.someMethod();
//        return true;
//    }
//
//
////    Анотація повинна реалізувати всі не реалізовані методи
////    public boolean otherMethod() {
////        return false;
////    }
//}
