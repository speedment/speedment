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
