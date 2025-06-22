package by.kotik.pollservice.util;

import by.kotik.pollservice.entity.Option;

import java.util.List;
import java.util.stream.IntStream;

public class OptionUtils {
    private OptionUtils() {
    }

    public static void sortOptionPositions(List<Option> options) {
        IntStream
                .range(0, options.size())
                .forEach(i -> options.get(i).setPosition(i + 1));
    }
}
