package com.kotov.lab1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ArrayChooser<T> implements Chooser<T> {
    private final Logger logger;
    private final T[] elements;
    private final Random random;

    @SafeVarargs
    public ArrayChooser(final T... args) {
        assert args != null && args.length != 0
            : "In ArrayChooser count of elements must be >= 1";

        logger = Logger.getLogger(this.getClass().getName());
        random = new Random();
        elements = args;
    }

    private T loggedGet(final int index) {
        logger.log(Level.INFO,
            String.format("Chosen element[%d] = %s", index,
                elements[index].toString()));
        return elements[index];
    }

    public T choose() {
        return loggedGet(random.nextInt(elements.length));
    }
}
