package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Method_ extends CodeModel {

    private boolean private_;
    private boolean static_;
    private boolean final_;
    private Type_ type_;
    private CharSequence name_;
    private Expression_ expression_;
    private Block_ block;
    private List<Field_> parameters; // Todo: Introduce parameter

    public Method_(Type_ type_, CharSequence name_) {
        this.parameters = new ArrayList<>();
        this.type_ = type_;
        this.name_ = name_;
    }

    public Method_ private_() {
        return private_(true);
    }

    public Method_ private_(boolean private_) {
        this.setPrivate_(private_);
        return this;
    }

    public Method_ static_() {
        return static_(true);
    }

    public Method_ static_(boolean static_) {
        this.setFinal_(static_);
        return this;
    }

    public Method_ final_(boolean final_) {
        this.setFinal_(final_);
        return this;
    }

    public Method_ final_() {
        return final_(true);
    }

    Method_ add(Field_ field_) {
        getParameters().add(field_);
        return this;
    }

    public boolean isPrivate_() {
        return private_;
    }

    public void setPrivate_(boolean private_) {
        this.private_ = private_;
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

    public Block_ getBlock() {
        return block;
    }

    public void setBlock(Block_ block) {
        this.block = block;
    }

    public List<Field_> getParameters() {
        return parameters;
    }

    public void setParameters(List<Field_> parameters) {
        this.parameters = parameters;
    }

	@Override
	public Type getType() {
		return Type.METHOD;
	}
}
