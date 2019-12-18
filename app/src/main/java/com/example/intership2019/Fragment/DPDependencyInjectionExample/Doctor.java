package com.example.intership2019.Fragment.DPDependencyInjectionExample;


import dagger.Component;

@Component( modules = RandomInjectionModule.class )
public interface Doctor {
    Body injectBlood();
}