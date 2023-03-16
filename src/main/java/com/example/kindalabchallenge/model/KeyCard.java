package com.example.kindalabchallenge.model;

public enum KeyCard {
    ADMIN, USER;

    public boolean isNotAdmin() {
        return this != ADMIN;
    }
}
