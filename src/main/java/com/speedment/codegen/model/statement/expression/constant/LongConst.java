package com.speedment.codegen.model.statement.expression.constant;

/**
 *
 * @author pemi
 */
public class LongConst extends Constant<LongConst, Long> {

    public static final LongConst NULL = new LongConst();
    public static final LongConst MINUS_ONE = new LongConst(-1l);
    public static final LongConst ZERO = new LongConst(0l);
    public static final LongConst ONE = new LongConst(1l);
    public static final LongConst TWO = new LongConst(2l);
    public static final LongConst FOUR = new LongConst(4l);
    public static final LongConst EIGHT = new LongConst(8l);
    public static final LongConst TEN = new LongConst(10l);
    public static final LongConst SIXTEEN = new LongConst(16l);

    public LongConst() {
    }

    public LongConst(Long value) {
        super(value);
    }

    @Override
    protected String labelClose() {
        return "l";
    }

}
