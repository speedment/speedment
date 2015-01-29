package com.speedment.codegen.model.statement.expression.constant;

import java.math.BigInteger;

/**
 *
 * @author pemi
 */
public class BigIntegerConst extends Constant<BigIntegerConst, BigInteger> {

    public static final BigIntegerConst NULL = new BigIntegerConst();
    public static final BigIntegerConst MINUS_ONE = new BigIntegerConst(new BigInteger("-1"));
    public static final BigIntegerConst ZERO = new BigIntegerConst(BigInteger.ZERO);
    public static final BigIntegerConst ONE = new BigIntegerConst(BigInteger.ONE);
    public static final BigIntegerConst TWO = new BigIntegerConst(new BigInteger("2"));
    public static final BigIntegerConst FOUR = new BigIntegerConst(new BigInteger("4"));
    public static final BigIntegerConst EIGHT = new BigIntegerConst(new BigInteger("8"));
    public static final BigIntegerConst TEN = new BigIntegerConst(BigInteger.TEN);
    public static final BigIntegerConst SIXTEEN = new BigIntegerConst(new BigInteger("16"));

    public BigIntegerConst() {
    }

    public BigIntegerConst(BigInteger value) {
        super(value);
    }

    @Override
    protected String labelClose() {
        return ")";
    }

    @Override
    protected String labelOpen() {
        return "new BigInteger(";
    }

}
