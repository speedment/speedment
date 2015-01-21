package com.speedment.codegen.model;

/**
 *
 * @author pemi
 */
public class Expression_ extends CodeModel {

    private String stringExpression;

    public Expression_(String stringExpression) {
        this.stringExpression = stringExpression;
    }

    public String getStringExpression() {
        return stringExpression;
    }

    public Expression_ setStringExpression(String stringExpression) {
        this.stringExpression = stringExpression;
        return this;
    }

	@Override
	public Type getType() {
		return Type.EXPRESSION;
	}
}
