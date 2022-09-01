package com.example.PermissionManagementSystem.enums;

public enum Mode {
    READ,
    WRITE,
    NONE;

    @Override
    public String toString() {
        switch (this) {
            case READ : return "READ";
            case WRITE : return "WRITE";
            case NONE : return "NONE";
        }
        return null;
    }
}
