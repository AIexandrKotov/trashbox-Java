package com.kotov.lab1;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ChanceChooser<T> implements Chooser<T> {
    public Random getRandom() {
        return random;
    }

    public void setRandom(final Random random) {
        this.random = random;
    }

    // Внутренний статический (без привязки к экземпляру верхнего класса) класс
    public static final class Element<T> {
        private final T element;
        private double chance;

        public Element(final T element, final double chance) {
            this.element = element;
            this.chance = chance;
        }

        @Override
        public String toString() {
            return String.format("[%s = %.2f%%]", getElement().toString(),
                getChance() * 100);
        }

        public T getElement() {
            return element;
        }

        public double getChance() {
            return chance;
        }

        public void setChance(final double chance) {
            this.chance = chance;
        }
    }

    private final Logger logger;
    private final Element<T>[] elements;
    private Random random;

    private void checkSum() {
        var sum = Arrays.stream(elements).mapToDouble(Element::getChance).sum();
        for (Element<T> element : elements) {
            element.setChance(element.getChance() / sum);
        }
    }

    private T loggedGet(final double chance, final int index) {
        logger.log(Level.INFO,
            String.format("Chance: %.2f%%, Chosen element[%d] = %s",
                chance * 100, index, elements[index].toString()));
        return elements[index].getElement();
    }

    @SafeVarargs
    public ChanceChooser(final Element<T>... args)
        throws IllegalArgumentException {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException(
                "In ChanceChooser count of elements must be >= 1");
        }
        if (Arrays.stream(args).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                "In ChanceChooser all of elements must be not null");
        }

        logger = Logger.getLogger(this.getClass().getName());
        setRandom(new Random());
        elements = args;

        checkSum();
    }

    @Override
    public T choose() {
        var chance = getRandom().nextDouble();
        var sum = 0.0D;
        for (int i = 0; i < elements.length; i++) {
            sum += elements[i].getChance();
            if (sum >= chance) {
                return loggedGet(chance, i);
            }
        }
        return loggedGet(chance, elements.length - 1);
    }
}
