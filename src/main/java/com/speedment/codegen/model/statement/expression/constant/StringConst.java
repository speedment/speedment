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
public class StringConst extends Constant<StringConst, String> {

    public static final StringConst NULL = new StringConst();
    public static final StringConst EMPTY = new StringConst("");
    public static final StringConst SPACE = new StringConst(" ");
    public static final StringConst COMMA = new StringConst(",");

    public StringConst() {
    }

    public StringConst(String value) {
        super(value);
    }

    @Override
    protected String labelOpen() {
        return "\"";
    }

    @Override
    protected String labelClose() {
        return "\"";
    }

}
