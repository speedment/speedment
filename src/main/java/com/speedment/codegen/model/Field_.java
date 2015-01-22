/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.codegen.model;

/**
 *
 * @author pemi
 */
public class Field_ implements CodeModel {

    protected boolean private_;
    protected boolean static_;
    protected boolean final_;
    private Type_ type_;
    private CharSequence name_;
    private Expression_ expression_;

    public Field_(Type_ type_, CharSequence name_) {
        this.type_ = type_;
        this.name_ = name_;
    }

    public Field_ private_() {
        return private_(true);
    }

    public Field_ private_(boolean private_) {
        this.private_ = private_;
        return this;
    }

    public Field_ static_() {
        return static_(true);
    }

    public Field_ static_(boolean static_) {
        this.final_ = static_;
        return this;
    }

    public Field_ final_(boolean final_) {
        this.final_ = final_;
        return this;
    }

    public Field_ final_() {
        return final_(true);
    }

//    public static class Builder extends Field_ {
//
//        public Builder(Type_ type_, CharSequence name_) {
//            super(type_, name_);
//        }
//
//        public Builder private_() {
//            return private_(true);
//        }
//
//        public Builder private_(boolean private_) {
//            this.private_ = private_;
//            return this;
//        }
//
//        public Builder static_(boolean static_) {
//            this.final_ = static_;
//            return this;
//        }
//
//        public Builder final_(boolean final_) {
//            this.final_ = final_;
//            return this;
//        }
//
//        public Field_ build() {
//            return new Field_(private_, static_, final_, type_, name_);
//        }
//
//    }
//
//    public static Builder builder(Type_ type_, CharSequence name_) {
//        return new Builder(type_, name_);
//    }

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
	
	@Override
	public Type getType() {
		return Type.FIELD;
	}

    public Type_ getType_() {
        return type_;
    }

    public void setType_(Type_ type_) {
        this.type_ = type_;
    }
}
