package com.example.intership2019.Fragment.DPObserver;

public interface Subject {
    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObserver();
}
