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
 * Java Operators (3.12 Operators).
 *
 * @author pemi
 */
public enum Operator_ implements CodeModel {

    ASSIGN("="), GREATER_THAN(">"), LESS_THAN("<"), NOT("!"), COMPLEMENT("~"), QUESTION_MARK("?"), COLON(":"), ARROW("->"),
    EQUALS("=="), GREATER_OR_EQAL(">="), LESS_OR_EQUAL("<="), NOT_EQUAL("!="),
    AND_LOGICAL("&&"), OR_LOGICAL("||"), INCREMENT("++"), DECREMENT("--"),
    PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/"), AND_BINARY("&"), OR_BINARY("|"), XOR_BINARY("^"), MODULO("%"),
    LEFT_SHIFT("<<"), RIGHT_SHIFT(">>"), RIGTH_SHIFT_ZEROIN(">>>"),
    ADD_AND_ASSIGN("+="), MINUS_AND_ASSINGN("-="), MULTIPLY_AND_ASSIGN("*="), DIVIDE_AND_ASSIGN("/="),
    MODULO_AND_ASSING("%="), LEFT_SHIFT_AND_ASSIGN("<<="), RIGHT_SHIFT_AND_ASSIGN(">>="), RIGHT_SHIFT_ZEROIN_AND_ASSIGN(">>>=");

    /*
     = > < ! ~ ? : ->
     == >= <= != && || ++ --
     + - * / & | ^ % << >> >>>
     += -= *= /= &= |= ^= %= <<= >>= >>>=
     */
    private final String text;

    private Operator_(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

	@Override
	public Type getType() {
		return Type.OPERATOR;
	}

}
