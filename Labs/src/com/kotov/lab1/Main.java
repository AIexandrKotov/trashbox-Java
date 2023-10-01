package com.kotov.lab1;
//  Тема 1. Обобщенное программирование
//  Тема 2. Интерфейсы, внутренние классы
//  Тема 3. Исключения, утверждения и протоколирование
//  Лабораторная работа 1.
//  Внутренние классы. Анонимные классы. Интерфейсы
//  Изучить понятия внутренних, вложенных, локальных, анонимных классов,
//      познакомиться с примерами их использования. Написать и протестировать классы в
//      коде на Java. Изучить свойства этих классов.

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.IntConsumer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public final class Main {
    private Main() {
    }

    /**
     * Outs elements based on Chooser&lt;T&gt;.
     *
     * @param <T>     Elements internal type
     * @param chooser Chooser of elements
     * @param count   Count of elements
     */
    public static <T> void outs(final Chooser<T> chooser, final int count) {
        // Использование анонимного класса (просто для вида, он тут вообще не нужен, даже пришлось глушить warning)
        //noinspection Convert2Lambda
        IntStream.range(0, count).forEach(new IntConsumer() {
            @Override
            public void accept(final int i) {
                System.out.printf("%s\n", chooser.choose().toString());
            }
        });
        System.out.println();
    }

    public static void main(final String[] args) {
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
        var mainLogger = Logger.getLogger(Main.class.getName());

        // Обзор отработки исключений и утверждений
        //noinspection Convert2MethodRef,RedundantCast
        for (var wrong : Arrays.asList(
            (Callable<Chooser<Integer>>) () -> new ChanceChooser<>(),
            (Callable<Chooser<Integer>>) () -> new ChanceChooser<>(null, null),
            (Callable<Chooser<Integer>>) () -> new ArrayChooser<>(),
            // это не ошибка без -ea (enable assertion)
            (Callable<Chooser<Integer>>) () -> new ArrayChooser<>(null, null)
            // это не ошибка
        )) {
            try {
                var o = wrong.call();
                throw new Error(
                    String.format("%s don't throws exception", o.toString()));
            } catch (Throwable e) {
                mainLogger.log(Level.WARNING, e.toString());
            }
        }

        //CHECKSTYLE
        var arrayChooser = new ArrayChooser<>(1, 10, 100);
        outs(arrayChooser, 5);


        var chanceChooser = new ChanceChooser<>(
            new ChanceChooser.Element<>(1, 0.1),
            new ChanceChooser.Element<>(10, 0.8),
            new ChanceChooser.Element<>(100, 0.1)
        );
        outs(chanceChooser, 5);


        var anyChooser = new ChanceChooser<Object>(
            new ChanceChooser.Element<>(Integer.MAX_VALUE, 1.0),
            new ChanceChooser.Element<>(
                "Волк слабее тигра и льва, но в цирке не выступает", 1.0),
            new ChanceChooser.Element<>(Double.POSITIVE_INFINITY, 1.0)
        );
        outs(anyChooser, 5);
    }
}
