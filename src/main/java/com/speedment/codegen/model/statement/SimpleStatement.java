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
package com.speedment.codegen.model.statement;

import java.util.Optional;

/**
 *
 * @author pemi
 * @param <T> The type of SimpleStatement
 */
public class SimpleStatement<T extends SimpleStatement<T>> extends BaseStatement<T> {

    private CharSequence statement;

    public SimpleStatement() {
    }

    public SimpleStatement(CharSequence statement) {
        this.statement = statement;
    }

    public Optional<CharSequence> get() {
        return Optional.ofNullable(statement);
    }

    public T set(CharSequence statement) {
        return with(statement, s -> this.statement = s);
    }

}
