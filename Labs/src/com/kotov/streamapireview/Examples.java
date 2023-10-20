package com.kotov.streamapireview;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public final class Examples {
    private Examples() {

    }

    /**
     * Минимальное значение из массива
     */
    public static void example01(final int[] arr) {
        System.out.println("\n01: Minimal value by the array");
        //Python:   min(arr)
        //
        //C#:       arr.Min();

        System.out.println(
            Arrays.stream(arr)
                .min()
                .orElseThrow()
        );
    }

    /**
     * Максимальное отрицательное значение из массива
     */
    public static void example02(final double[] arr) {
        System.out.println("\n02: Maximal negative value by the array");
        //Python:   max(x for x in arr if x < 0)
        //
        //C#:       arr.Where(x => x < 0).Max();

        System.out.println(
            Arrays.stream(arr)
                .filter(x -> x < 0)
                .max()
                .orElseThrow()
        );
    }

    /**
     * Среднее значение корней по элементам массива
     */
    public static void example03(final double[] arr) {
        System.out.println("\n03: Average value by the square roots of array");
        //Python:   sum(x**0.5 for x in arr if x >= 0) / len(x for x in x >= 0)
        //
        //C#:       arr.Where(x => x >= 0).Select(Math.Sqrt).Average();

        System.out.println(
            Arrays.stream(arr)
                .filter(x -> x >= 0)
                .map(Math::sqrt)
                .average()
                .orElseThrow()
        );
    }

    /**
     * Самый часто встречающийся элемент массива (элемент и число встреч)
     */
    public static void example04(final int[] arr) {
        System.out.println(
            "\n04: Most frequent value in array with count of entries");
        //Python:   entries = {x:arr.count(x) for x in set(arr)}
        //          key = max(entries.keys(), key=lambda k: entries[k])
        //          print(f"{key} ({entries[key]})")
        //
        //C#:       var entry = arr.GroupBy(x => x).OrderBy(x => x.Count()).First();
        //          Console.WriteLine($"{entry.Key} ({entry.Count()})");

        var entry = Arrays.stream(arr)
            .boxed()
            .collect(Collectors.groupingBy(i -> i))
            .entrySet()
            .stream()
            .min(Comparator.comparingLong(x -> (long) x.getValue().size()))
            .orElseThrow();

        System.out.printf("%d (%d)\n",
            entry.getKey(),
            (long) entry.getValue().size());
    }

    /**
     * Вывести самые красивые числа среди переданных
     */
    public static void example05(final int[] arr) {
        System.out.println("\n05: Most beautiful values in array");
        //Python:   pain = [(len(set(str(x))), x) for x in arr]
        //          m = min(a[0] for a in pain)
        //          [x[1] for x in pain if x[0] == m]
        //
        //C#:       arr.GroupBy(x => x.ToString().ToCharArray().Distinct().Count()).OrderBy(x => x.Key).First();
        //
        //Тест производительности данного алгоритма для 1kk значений:
        //Java SE 20:   513 ms
        //Python 3.11:  385 ms
        //C# .NET 6:    240 ms
        //Rust 1.73:    190 ms

        Arrays.stream(arr)
            .boxed()
            .collect(Collectors.groupingBy(
                i -> i.toString()
                    .chars()
                    .mapToObj(c -> (char) c)
                    .distinct()
                    .count())
            )
            .entrySet()
            .stream()
            .min(Comparator.comparingLong(Map.Entry::getKey))
            .orElseThrow()
            .getValue()
            .forEach(System.out::println);
    }
}
