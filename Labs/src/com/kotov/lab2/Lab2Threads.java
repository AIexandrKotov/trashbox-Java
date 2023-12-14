package com.kotov.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public final class Lab2Threads {
    private Lab2Threads() {

    }
    private static ArrayList<Integer> integers;
    private static final Object SYNC = new Object();
    private static final Random RANDOM = new Random();

    public static void status() {
        synchronized (SYNC) {
            System.out.printf(
                "(%d) [%s] = %d\n", integers.size(),
                    integers.stream().limit(10).map(Object::toString).collect(Collectors.joining(", "))
                        + (integers.size() > 10 ? "..." : ""),
                    integers.stream().mapToInt(x -> x).sum()
                );
        }
    }

    private static boolean runningInMainThread = true;
    public static void init() throws InterruptedException {
        System.out.println("---lovely java threads-------");

        integers = Arrays
            .stream((new Integer[] {1, 2, 3, 4}))
            .collect(Collectors.toCollection(ArrayList::new));

        var adderThread = new Thread(() -> {
            while (true) {
                synchronized (SYNC) {
                    integers.add(RANDOM.nextInt(0, 10));
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {

                }
            }
        });
        adderThread.setName("ADDER");
        adderThread.setPriority(10);

        var removerThread = new Thread(() -> {
            while (true) {
                synchronized (SYNC) {
                    if (integers.isEmpty()) {
                        continue;
                    }
                    integers.remove(RANDOM.nextInt(integers.size()));
                }
                try {
                    Thread.sleep(300);
                } catch (Exception ignored) {

                }
            }
        });
        removerThread.setName("REMOVER");
        adderThread.setPriority(1);

        adderThread.start();
        removerThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            runningInMainThread = false;
            adderThread.interrupt();
            removerThread.interrupt();
        }));

        //     Прочее
        // Thread.run - start в текущем потоке
        // Thread.join - ждать выполнения
        // Thread.isAlive - поток выполняется
        // Thread.isInterrupred - поток приостановлен
        while (runningInMainThread) {
            Lab2Threads.status();
            Thread.sleep(500);
        }
    }
}
