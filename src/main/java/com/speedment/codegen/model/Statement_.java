package com.speedment.codegen.model;

/**
 *
 * @author pemi
 */
public class Statement_ {

    private CharSequence statementText;

    public Statement_(CharSequence statementText) {
        this.statementText = statementText;
    }

    public CharSequence getStatementText() {
        return statementText;
    }

    public void setStatementText(CharSequence statementText) {
        this.statementText = statementText;
    }

}
