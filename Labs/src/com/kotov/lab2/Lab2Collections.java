package com.kotov.lab2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Lab2Collections {
    private Lab2Collections() {

    }

    public static <T> String joinToStr(final Stream<T> stream) {
        return stream.map(Object::toString).collect(Collectors.joining(", "));
    }
    public static <T> String joinToStr(final Collection<T> collection) {
        return joinToStr(collection.stream());
    }

    public static <T> void getCollectionInfo(final Collection<T> collection) {
        System.out.printf("---%s---%n", collection.getClass().getName().replace("java.util.", ""));
        System.out.print("Interfaces: ");
        System.out.print("Iterable");
        if (collection instanceof Set<T>) {
            System.out.print(", Set");
            if (collection instanceof SortedSet<T>) {
                System.out.print(", SortedSet");
            }
        }
        if (collection instanceof List<T>) {
            System.out.print(", List");
        }
        if (collection instanceof Queue<T>) {
            System.out.print(", Queue");
        }
        if (collection instanceof Deque<T>) {
            System.out.print(", Deque");
        }
        System.out.println();
        System.out.println(joinToStr(collection));
        System.out.println();
    }

    public static void hashSet() {
        var set = new HashSet<>(List.of(1, 1, 2, 2, 3, 3));
        getCollectionInfo(set);

        var linkedSet = new LinkedHashSet<Integer>();
        linkedSet.add(0);
        linkedSet.addAll(List.of(1, 2, 3, 4));
        linkedSet.remove(3);
        getCollectionInfo(linkedSet);

        var treeSet = new TreeSet<>(set);
        getCollectionInfo(treeSet);
    }

    public static void list() {
        var arrayList = new ArrayList<>(IntStream.range(0, 20).boxed().toList());
        getCollectionInfo(arrayList);

        var linkedList = new LinkedList<>(arrayList);
        linkedList.remove(14);
        getCollectionInfo(linkedList);
    }

    public static void deque() {
        var arrayDeque = new ArrayDeque<>(List.of(1, 2, 3, 4, 5));
        arrayDeque.addFirst(6);
        arrayDeque.addLast(7);
        getCollectionInfo(arrayDeque);
    }

    public static void map() {
        var pollution = new HashMap<>(Map.of(
            "Ростов-на-Дону", 90,
            "Москва", 90,
            "Краснодар", 75,
            "Мурманск", 20,
            "Рыбинск", 5
        ));
        getCollectionInfo(pollution.entrySet());

        // отсортированный по значениям вывод
        var sorted = joinToStr(pollution.entrySet().stream().sorted(
            Comparator.comparingInt(Map.Entry::getValue)).map(x -> "%s %s".formatted(x.getKey(), x.getValue())));

        var linkedHashMap = new LinkedHashMap<>(pollution);
        getCollectionInfo(linkedHashMap.entrySet());

        var treeMap = new TreeMap<>(pollution);
        getCollectionInfo(treeMap.entrySet());
    }

    public static void doAll() {
        hashSet();
        list();
        deque();
        map();
    }

}
