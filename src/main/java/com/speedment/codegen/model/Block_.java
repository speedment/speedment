package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Block_ extends CodeModel {

    private final List<Statement_> statements;

    public Block_() {
        this.statements = new ArrayList<>();
    }

    Block_ add(Statement_ statement_) {
        getStatements().add(statement_);
        return this;
    }

    public List<Statement_> getStatements() {
        return statements;
    }

	@Override
	public Type getType() {
		return Type.BLOCK;
	}
}
