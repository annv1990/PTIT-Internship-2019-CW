package com.example.intership2019.Fragment.DPDependencyInjection;

import com.example.intership2019.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPrefModule.class})
public interface MyComponent {
    void inject(MainActivity activity);
}