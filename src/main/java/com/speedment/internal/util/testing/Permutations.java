package com.speedment.internal.util.testing;

import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * General Permutation support from
 * http://minborgsjavapot.blogspot.com/2015/07/java-8-master-permutations.html
 *
 * @author Per Minborg
 */
public class Permutations {

    public static long factorial(final int n) {
        if (n > 20 || n < 0) {
            throw new IllegalArgumentException(n + " is out of range");
        }
        return LongStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b);
    }

    public static <T> List<T> permutation(final long no, final List<T> items) {
        return permutationHelper(no,
            new LinkedList<>(Objects.requireNonNull(items)),
            new ArrayList<>());
    }

    private static <T> List<T> permutationHelper(final long no, final LinkedList<T> in, final List<T> out) {
        if (in.isEmpty()) {
            return out;
        }
        final long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)));
        return permutationHelper((int) (no % subFactorial), in, out);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<Stream<T>> of(final T... items) {
        final List<T> itemList = Arrays.asList(items);
        return LongStream.range(0, factorial(items.length))
            .mapToObj(no -> permutation(no, itemList).stream());
    }

    private Permutations() {
        instanceNotAllowed(getClass());
    }

}
