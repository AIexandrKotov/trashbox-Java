package com.kotov.lab2;

import java.util.function.Predicate;
import java.util.Random;
import java.util.stream.Collectors;

public final class Main {
    private Main() {

    }

    public static void main(final String[] args) {
        var rand = new Random();
        var ints = rand.ints(0, 10).limit(5).boxed().toList();

        System.out.println("Все числа: "
            + ints
            .stream()
            .map(Object::toString)
            .collect(Collectors.joining(", ")));

        // Пример встроенного функционального интерфейса - Predicate<T>
        Predicate<Integer> isEven = x -> x % 2 == 0;

        System.out.println("Из них чётные: "
            + ints
            .stream()
            .filter(isEven)
            .map(Object::toString)
            .collect(Collectors.joining(", ")));
    }
}
