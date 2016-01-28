package com.speedment.internal.util.function;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 *
 * @author Per Minborg
 */
public class FunctionCastUtil {

    public static <T, U> BiConsumer<T, U> as(BiConsumer<T, U> item) {
        return item;
    }

    public static <T, U, R> BiFunction<T, U, R> as(BiFunction<T, U, R> item) {
        return item;
    }

    public static <T> BinaryOperator<T> as(BinaryOperator<T> item) {
        return item;
    }

    public static <T, U> BiPredicate<T, U> as(BiPredicate<T, U> item) {
        return item;
    }

    public static BooleanSupplier as(BooleanSupplier item) {
        return item;
    }

    public static <T> Consumer<T> as(Consumer<T> item) {
        return item;
    }

    public static DoubleBinaryOperator as(DoubleBinaryOperator item) {
        return item;
    }

    public static DoubleConsumer as(DoubleConsumer item) {
        return item;
    }

    public static <R> DoubleFunction<R> as(DoubleFunction<R> item) {
        return item;
    }

    public static DoublePredicate as(DoublePredicate item) {
        return item;
    }

    public static DoubleToIntFunction as(DoubleToIntFunction item) {
        return item;
    }

    public static DoubleToLongFunction as(DoubleToLongFunction item) {
        return item;
    }

    public static DoubleUnaryOperator as(DoubleUnaryOperator item) {
        return item;
    }

    public static <T, R> Function<T, R> as(Function<T, R> item) {
        return item;
    }

    public static IntBinaryOperator as(IntBinaryOperator item) {
        return item;
    }

    public static IntConsumer as(IntConsumer item) {
        return item;
    }

    public static <R> IntFunction<R> as(IntFunction<R> item) {
        return item;
    }

    public static IntPredicate as(IntPredicate item) {
        return item;
    }

    public static IntSupplier as(IntSupplier item) {
        return item;
    }

    public static IntToDoubleFunction as(IntToDoubleFunction item) {
        return item;
    }

    public static IntToLongFunction as(IntToLongFunction item) {
        return item;
    }

    public static IntUnaryOperator as(IntUnaryOperator item) {
        return item;
    }

    public static LongBinaryOperator as(LongBinaryOperator item) {
        return item;
    }

    public static LongConsumer as(LongConsumer item) {
        return item;
    }

    public static <R> LongFunction<R> as(LongFunction<R> item) {
        return item;
    }

    public static LongPredicate as(LongPredicate item) {
        return item;
    }

    public static <T> LongSupplier as(LongSupplier item) {
        return item;
    }

    public static LongToDoubleFunction as(LongToDoubleFunction item) {
        return item;
    }

    public static LongToIntFunction as(LongToIntFunction item) {
        return item;
    }

    public static LongUnaryOperator as(LongUnaryOperator item) {
        return item;
    }

    public static <T> ObjDoubleConsumer<T> as(ObjDoubleConsumer<T> item) {
        return item;
    }

    public static <T> ObjIntConsumer<T> as(ObjIntConsumer<T> item) {
        return item;
    }

    public static <T> ObjLongConsumer<T> as(ObjLongConsumer<T> item) {
        return item;
    }

    public static <T> Predicate<T> as(Predicate<T> item) {
        return item;
    }

    public static <T> Supplier<T> as(Supplier<T> item) {
        return item;
    }

    public static <T, U> ToDoubleBiFunction<T, U> as(ToDoubleBiFunction<T, U> item) {
        return item;
    }

    public static <T> ToDoubleFunction<T> as(ToDoubleFunction<T> item) {
        return item;
    }

    public static <T, U> ToIntBiFunction<T, U> as(ToIntBiFunction<T, U> item) {
        return item;
    }

    public static <T> ToIntFunction<T> as(ToIntFunction<T> item) {
        return item;
    }

    public static <T, U> ToLongBiFunction<T,U> as(ToLongBiFunction<T,U> item) {
        return item;
    }

    public static <T> ToLongFunction<T> as(ToLongFunction<T> item) {
        return item;
    }
    public static <T> UnaryOperator<T> as(UnaryOperator<T> item) {
        return item;
    }

    private FunctionCastUtil() {
    }

}
