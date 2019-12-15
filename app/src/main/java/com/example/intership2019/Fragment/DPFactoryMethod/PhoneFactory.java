package com.example.intership2019.Fragment.DPFactoryMethod;

public class PhoneFactory {

    public static Phone creatPhone(PhoneType phoneType, String shape, String param) {

        Phone phone = null;
        switch (phoneType) {
            case SAMSUNG:
                phone = new Samsung(shape, param);
                break;
            case IPHONE:
                phone = new Iphone(shape, param);
                break;
        }

        return phone;
    }
}
