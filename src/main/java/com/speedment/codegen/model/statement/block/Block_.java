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
package com.speedment.codegen.model.statement.block;

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.statement.SimpleStatement;
import com.speedment.codegen.model.statement.Statement_;
import com.speedment.util.stream.StreamUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class Block_<T extends Block_<T>> extends SimpleStatement<T> {

    private final List<Statement_> statements;

    public Block_() {
        this.statements = new ArrayList<>();
    }

    public T add(Statement_... statements_) {
        return addSeveral(statements_, statements::add);
    }

    @Override
    public List<Statement_> getStatements() {
        return statements;
    }

    @Override
    public T set(CharSequence statement) {
        throw new UnsupportedOperationException("set() is unsupported for " + getClass().getSimpleName());
    }

    @Override
    public Type getModelType() {
        // Todo: Change to Type.STATEMENT ???
        return Type.BLOCK;
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.<CodeModel>of(statements);
    }
}
