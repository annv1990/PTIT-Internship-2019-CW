package com.example.intership2019.Fragment.DPAbstractFactory;

public class ServerFactory implements ComputerAbstractFactory {

    private String RAM;
    private String HDD;

    public ServerFactory(String ram, String hdd, String cpu) {
        this.RAM = ram;
        this.HDD = hdd;
        this.CPU = cpu;
    }

    private String CPU;
    @Override
    public Computer createComputer() {
        return new Server(this.RAM, this.HDD, this.CPU);
    }
}
