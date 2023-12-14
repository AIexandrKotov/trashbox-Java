package com.kotov.lab2;

public final class Main {
    private Main() {

    }

    public static void main(final String[] args) throws InterruptedException {
        Lab2Collections.doAll();
        Lab2Threads.init();
    }
}
