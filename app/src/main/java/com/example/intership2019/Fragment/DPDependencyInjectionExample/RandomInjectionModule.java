package com.example.intership2019.Fragment.DPDependencyInjectionExample;


import java.util.Random;

import dagger.Module;
import dagger.Provides;

@Module
public class RandomInjectionModule {
    private static Blood blood;

    public RandomInjectionModule() {
    }

    @Provides
    static Blood provideBlood() {
        if (blood != null) {
            return blood;
        }
        Random rnd = new Random();
        int rndNumber = rnd.nextInt(3);
        switch (rndNumber) {
            case 0: blood = new A_Blood(); break;
            case 1: blood = new B_Blood(); break;
            case 2: blood = new AB_Blood(); break;
            case 3: blood = new O_Blood(); break;
            default: blood = new A_Blood();
        }
        return blood;
    }
}