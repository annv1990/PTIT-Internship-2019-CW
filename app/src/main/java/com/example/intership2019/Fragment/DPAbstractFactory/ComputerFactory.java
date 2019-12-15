package com.example.intership2019.Fragment.DPAbstractFactory;

public class ComputerFactory {
    public static Computer createComputer(ComputerAbstractFactory computerAbstractFactory) {
        return computerAbstractFactory.createComputer();
    }
}
