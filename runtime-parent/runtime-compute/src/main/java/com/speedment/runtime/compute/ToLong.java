package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.trait.HasAbs;
import com.speedment.runtime.compute.trait.HasAsDouble;
import com.speedment.runtime.compute.trait.HasAsInt;
import com.speedment.runtime.compute.trait.HasAsLong;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasDivide;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMinus;
import com.speedment.runtime.compute.trait.HasMultiply;
import com.speedment.runtime.compute.trait.HasNegate;
import com.speedment.runtime.compute.trait.HasPlus;
import com.speedment.runtime.compute.trait.HasPow;
import com.speedment.runtime.compute.trait.HasSign;
import com.speedment.runtime.compute.trait.HasSqrt;

import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;

/**
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToLong<T>
extends Expression,
        ToLongFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasAbs<ToLong<T>>,
        HasSign<ToByte<T>>,
        HasSqrt<ToDouble<T>>,
        HasNegate<ToLong<T>>,
        HasPow<T, ToLong<T>, ToDouble<T>>,
        HasPlus<T, ToLong<T>, ToLong<T>, ToLong<T>>,
        HasMinus<T, ToLong<T>, ToLong<T>, ToLong<T>>,
        HasMultiply<T, ToLong<T>, ToLong<T>, ToLong<T>>,
        HasDivide<T, ToLong<T>>,
        HasHash<T>,
        HasCompare<T> {

    long applyAsLong(T object);

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.LONG;
    }

    @Override
    default ToDouble<T> asDouble() {
        return this::applyAsLong;
    }

    @Override
    default ToInt<T> asInt() {
        return object -> (int) applyAsLong(object);
    }

    @Override
    default ToLong<T> asLong() {
        return this;
    }

    default ToDouble<T> mapToDouble(LongToDoubleFunction operator) {
        return object -> operator.applyAsDouble(applyAsLong(object));
    }

    default ToLong<T> map(LongUnaryOperator operator) {
        return object -> operator.applyAsLong(applyAsLong(object));
    }

    @Override
    default ToLong<T> abs() {
        return Expressions.abs(this);
    }

    @Override
    default ToByte<T> sign() {
        return Expressions.sign(this);
    }

    @Override
    default ToDouble<T> sqrt() {
        return Expressions.sqrt(this);
    }

    @Override
    default ToLong<T> pow(byte power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToLong<T> pow(int power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToDouble<T> pow(double power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToLong<T> pow(ToByte<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToLong<T> pow(ToInt<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToDouble<T> pow(ToDouble<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToLong<T> plus(byte other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToLong<T> plus(ToByte<T> other) {
        return Expressions.plus(this, other.asLong());
    }

    @Override
    default ToLong<T> plus(int other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToLong<T> plus(ToInt<T> other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToLong<T> plus(long other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToLong<T> plus(ToLong<T> other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> plus(double other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> plus(ToDouble<T> other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToLong<T> minus(byte other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToLong<T> minus(ToByte<T> other) {
        return Expressions.minus(this, other.asLong());
    }

    @Override
    default ToLong<T> minus(int other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToLong<T> minus(ToInt<T> other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToLong<T> minus(long other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToLong<T> minus(ToLong<T> other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> minus(double other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> minus(ToDouble<T> other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToLong<T> multiply(byte other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToLong<T> multiply(ToByte<T> other) {
        return Expressions.multiply(this, other.asLong());
    }

    @Override
    default ToLong<T> multiply(int other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToLong<T> multiply(ToInt<T> other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToLong<T> multiply(long other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToLong<T> multiply(ToLong<T> other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToDouble<T> multiply(double other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> multiply(ToDouble<T> other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToLong<T> divideFloor(byte divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToLong<T> divideFloor(ToByte<T> divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToLong<T> divideFloor(int divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToLong<T> divideFloor(ToInt<T> divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToLong<T> divideFloor(long divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToLong<T> divideFloor(ToLong<T> divisor) {
        return Expressions.divideFloor(this, divisor);
    }

    @Override
    default ToDouble<T> divide(byte divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToByte<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(int divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToInt<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(long divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToLong<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(double divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToDouble<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToLong<T> negate() {
        return Expressions.negate(this);
    }
    
    @Override
    default long hash(T object) {
        final long l = applyAsLong(object);
        return (int) (l ^ (l >>> 32));
    }

    @Override
    default int compare(T first, T second) {
        return Long.compare(
            applyAsLong(first),
            applyAsLong(second)
        );
    }
}
