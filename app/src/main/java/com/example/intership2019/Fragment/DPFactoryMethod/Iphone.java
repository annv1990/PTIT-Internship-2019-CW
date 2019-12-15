package com.example.intership2019.Fragment.DPFactoryMethod;

public class Iphone implements Phone {

    private String Shape;
    private String Param;

    public Iphone(String shape, String param) {
        Shape = shape;
        Param = param;
    }

    @Override
    public String descriptionShape() {
        return this.Shape;
    }

    @Override
    public String descriptionParam() {
        return this.Param;
    }
}
