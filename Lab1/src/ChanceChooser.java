import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChanceChooser<T> implements Chooser<T> {
    public static class Element<T> {
        public T element;
        public double chance;
        public Element(T element, double chance){
            this.element = element;
            this.chance = chance;
        }

        @Override
        public String toString() {
            return String.format("[%s = %.2f%%]", element.toString(), chance * 100);
        }
    }

    private final Logger logger;
    private final Element<T>[] elements;
    public Random random;

    private void checkSum() {
        var sum = Arrays.stream(elements).mapToDouble(e -> e.chance).sum();
        for (Element<T> element : elements)
            element.chance /= sum;
    }

    @SafeVarargs
    public ChanceChooser(Element<T>... args)
    {
        logger = Logger.getLogger(this.getClass().getName());
        if (args == null || args.length == 0)
            throw new IllegalArgumentException("In ChanceChooser count of elements must be >= 1");
        if (Arrays.stream(args).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Вы кто такие, чтоб это делать?");

        random = new Random();
        elements = args;

        checkSum();
    }

    @Override
    public T choose() {
        var chance = random.nextDouble();
        var sum = 0.0D;
        for (int i = 0; i < elements.length; i++) {
            sum += elements[i].chance;
            if (sum >= chance) {
                logger.log(Level.INFO,
                        String.format("Chance: %f, Choosed element[%d] = %s", chance, i, elements[i].toString()));
                return elements[i].element;
            }
        }
        logger.log(Level.INFO,
                String.format("Chance: %f, Choosed element[%d] = %s",
                        chance,
                        elements.length - 1,
                        elements[elements.length - 1].toString()));
        return elements[elements.length - 1].element;
    }
}
