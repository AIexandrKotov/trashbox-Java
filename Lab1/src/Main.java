
//  Тема 1. Обобщенное программирование
//  Тема 2. Интерфейсы, внутренние классы
//  Тема 3. Исключения, утверждения и протоколирование
//  Лабораторная работа 1.
//  Внутренние классы. Анонимные классы. Интерфейсы
//  Изучить понятия внутренних, вложенных, локальных, анонимных классов,
//      познакомиться с примерами их использования. Написать и протестировать классы в
//      коде на Java. Изучить свойства этих классов.

import java.lang.Integer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);

        var chanceChooser = new ChanceChooser<Integer>(
                new ChanceChooser.Element<Integer>(1, 1.0),
                new ChanceChooser.Element<Integer>(10, 1.0),
                new ChanceChooser.Element<Integer>(100, 1.0)
        );

        for (var i = 0; i < 10; i++)
            System.out.printf("%s\n", chanceChooser.choose().toString());
    }
}