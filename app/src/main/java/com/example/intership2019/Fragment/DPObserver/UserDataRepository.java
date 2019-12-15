package com.example.intership2019.Fragment.DPObserver;

import android.os.Handler;

import java.util.ArrayList;

public class UserDataRepository implements Subject {

    private String mFullName;
    private int mAge;
    private static UserDataRepository INSTANCE = null;

    private ArrayList<Observer> mObserverArrayList;

    private UserDataRepository() {
        mObserverArrayList = new ArrayList<>();
        getNewDataFromRemote();
    }

    // Simulate network
    private void getNewDataFromRemote() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setUserData("Design pattern Observer", 21);
            }
        }, 10000);
    }

    // Creates a Singleton of the class
    public static UserDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository();
        }
        return INSTANCE;
    }


    public void setUserData(String fullName, int age) {
        mFullName = fullName;
        mAge = age;
        notifyObserver();
    }

    @Override
    public void addObserver(Observer observer) {
        if (!mObserverArrayList.contains(observer)) {
            mObserverArrayList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (mObserverArrayList.contains(observer)) {
            mObserverArrayList.remove(observer);
        }
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : mObserverArrayList) {
            observer.onChange(mFullName, mAge);
        }
    }
}
