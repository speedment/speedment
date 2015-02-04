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
public class LongConst extends Constant<LongConst, Long> {

    public static final LongConst NULL = new LongConst();
    public static final LongConst MINUS_ONE = new LongConst(-1l);
    public static final LongConst ZERO = new LongConst(0l);
    public static final LongConst ONE = new LongConst(1l);
    public static final LongConst TWO = new LongConst(2l);
    public static final LongConst FOUR = new LongConst(4l);
    public static final LongConst EIGHT = new LongConst(8l);
    public static final LongConst TEN = new LongConst(10l);
    public static final LongConst SIXTEEN = new LongConst(16l);

    public LongConst() {
    }

    public LongConst(Long value) {
        super(value);
    }

    @Override
    protected String labelClose() {
        return "l";
    }

}
