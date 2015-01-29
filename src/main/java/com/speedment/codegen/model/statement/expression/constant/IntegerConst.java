package com.speedment.codegen.model.statement.expression.constant;

/**
 *
 * @author pemi
 */
public class IntegerConst extends Constant<IntegerConst, Integer> {

    public static final IntegerConst NULL = new IntegerConst();
    public static final IntegerConst MINUS_ONE = new IntegerConst(-1);
    public static final IntegerConst ZERO = new IntegerConst(0);
    public static final IntegerConst ONE = new IntegerConst(1);
    public static final IntegerConst TWO = new IntegerConst(2);
    public static final IntegerConst FOUR = new IntegerConst(4);
    public static final IntegerConst EIGHT = new IntegerConst(8);
    public static final IntegerConst TEN = new IntegerConst(10);
    public static final IntegerConst SIXTEEN = new IntegerConst(16);

    public IntegerConst() {
    }

    public IntegerConst(Integer value) {
        super(value);
    }

}
