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

import java.math.BigInteger;

/**
 *
 * @author pemi
 */
public class BigIntegerConst extends Constant<BigIntegerConst, BigInteger> {

    public static final BigIntegerConst NULL = new BigIntegerConst();
    public static final BigIntegerConst MINUS_ONE = new BigIntegerConst(new BigInteger("-1"));
    public static final BigIntegerConst ZERO = new StaticBigIntegerConst(BigInteger.ZERO, "BigInteger.ZERO");
    public static final BigIntegerConst ONE = new StaticBigIntegerConst(BigInteger.ONE, "BigInteger.ONE");
    public static final BigIntegerConst TWO = new BigIntegerConst(new BigInteger("2"));
    public static final BigIntegerConst FOUR = new BigIntegerConst(new BigInteger("4"));
    public static final BigIntegerConst EIGHT = new BigIntegerConst(new BigInteger("8"));
    public static final BigIntegerConst TEN = new StaticBigIntegerConst(BigInteger.TEN, "BigInteger.TEN");
    public static final BigIntegerConst SIXTEEN = new BigIntegerConst(new BigInteger("16"));

    public BigIntegerConst() {
    }

    public BigIntegerConst(BigInteger value) {
        super(value);
    }

    @Override
    protected String labelClose() {
        return ")";
    }

    @Override
    protected String labelOpen() {
        return "new BigInteger(";
    }

    private static class StaticBigIntegerConst extends BigIntegerConst {

        private final String toStringValue;

        public StaticBigIntegerConst(BigInteger value, String toStringValue) {
            super(value);
            this.toStringValue = toStringValue;
        }

        @Override
        public String toString() {
            return toStringValue;
        }

    }

}
