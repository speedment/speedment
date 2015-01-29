package com.speedment.codegen.model.statement.expression.constant;

/**
 *
 * @author pemi
 */
public class StringConst extends Constant<StringConst, String> {

    public static final StringConst NULL = new StringConst();
    public static final StringConst EMPTY = new StringConst("");
    public static final StringConst SPACE = new StringConst(" ");
    public static final StringConst COMMA = new StringConst(",");

    public StringConst() {
    }

    public StringConst(String value) {
        super(value);
    }

    @Override
    protected String labelOpen() {
        return "\"";
    }

    @Override
    protected String labelClose() {
        return "\"";
    }

}
