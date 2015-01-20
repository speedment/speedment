package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Method_ extends CodeModel {

    private boolean private_;
	private boolean protected_;
	private boolean public_;
    private boolean static_;
    private boolean final_;
    private Type_ type_;
    private CharSequence name_;
    private Expression_ expression_;
    private List<Field_> parameters; // Todo: Introduce parameter
    private List<Statement_> statements; // Todo: Block instead of statements.

    public Method_(Type_ type_, CharSequence name_) {
        this.parameters = new ArrayList<>();
        this.statements = new ArrayList<>();
        this.type_ = type_;
        this.name_ = name_;
    }

    public Method_ add(Field_ field_) {
        getParameters().add(field_);
        return this;
    }

    public Method_ add(Statement_ statement) {
        statements.add(statement);
        return this;
    }

    public boolean isPrivate_() {
        return private_;
    }

    public void setPrivate_(boolean private_) {
        this.private_ = private_;
    }

	public boolean isProtected_() {
		return protected_;
	}

	public void setProtected_(boolean protected_) {
		this.protected_ = protected_;
	}

	public boolean isPublic_() {
		return public_;
	}

	public void setPublic_(boolean public_) {
		this.public_ = public_;
	}

    public boolean isStatic_() {
        return static_;
    }

    public void setStatic_(boolean static_) {
        this.static_ = static_;
    }

    public boolean isFinal_() {
        return final_;
    }

    public void setFinal_(boolean final_) {
        this.final_ = final_;
    }

    public Type_ getType_() {
        return type_;
    }

    public void setType_(Type_ type_) {
        this.type_ = type_;
    }

    public CharSequence getName_() {
        return name_;
    }

    public void setName_(CharSequence name_) {
        this.name_ = name_;
    }

    public Expression_ getExpression_() {
        return expression_;
    }

    public void setExpression_(Expression_ expression_) {
        this.expression_ = expression_;
    }

    public List<Field_> getParameters() {
        return parameters;
    }

    public void setParameters(List<Field_> parameters) {
        this.parameters = parameters;
    }
	
	public void setBlock_(Block_ block_) {
		throw new UnsupportedOperationException();
		// TODO: Implement Method_.setBlock_
	}
	
	public Block_ getBlock_() {
		throw new UnsupportedOperationException();
		// TODO: Implement Method_.getBlock_
	}

	@Override
	public Type getType() {
		return Type.METHOD;
	}
}
