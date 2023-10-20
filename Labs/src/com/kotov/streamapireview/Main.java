package com.kotov.streamapireview;

import java.util.Random;

public final class Main {
    private Main() {

    }

    public static void main(final String[] args) {
        var rand = new Random();
        var ints = rand.ints(-100000, 100000).limit(200).toArray();
        var doubles = rand.doubles(-100000, 100000).limit(200).toArray();

        Examples.example01(ints);
        Examples.example02(doubles);
        Examples.example03(doubles);
        Examples.example04(ints);
        Examples.example05(ints);
    }
}
