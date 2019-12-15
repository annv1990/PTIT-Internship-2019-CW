package com.example.intership2019.Fragment.DPPrototype;

public class WinOS implements Cloneable {
    private String os;
    private String office;
    private String antivirus;
    private String browser;
    private String others;

    public WinOS(String os, String office, String antivirus, String browser, String others) {
        super();
        this.os = os;
        this.office = office;
        this.antivirus = antivirus;
        this.browser = browser;
        this.others = others;
    }

    @Override
    public WinOS clone() {
        try {
            return (WinOS) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public String toString() {
        return "WinOS{" +
                "os='" + os + '\'' +
                ", office='" + office + '\'' +
                ", antivirus='" + antivirus + '\'' +
                ", browser='" + browser + '\'' +
                ", others='" + others + '\'' +
                '}';
    }
}
