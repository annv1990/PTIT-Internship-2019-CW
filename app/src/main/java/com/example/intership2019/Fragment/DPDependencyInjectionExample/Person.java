package com.example.intership2019.Fragment.DPDependencyInjectionExample;

import javax.inject.Inject;

public class Person{
    Body body;
    @Inject
    public Person(Body body){
        this.body = body;
    }
}
