package com.example.intership2019.Fragment.DPBuilder;

import java.util.List;

public class User {
    private String name;
    private int age;
    private List<String> languages;

    public static class Builder {
        private String name;
        private int age;
        private List<String> languages;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder languages(List<String> languages) {
            this.languages = languages;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder){
        name = builder.name;
        age = builder.age;
        languages = builder.languages;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", languages=" + languages +
                '}';
    }
}
