package com.kotov.lab1;

@FunctionalInterface
public interface Chooser<T> {
    T choose();
}
