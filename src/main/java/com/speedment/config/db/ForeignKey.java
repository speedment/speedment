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
package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.ForeignKeyMutator;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface ForeignKey extends
        Document,
        HasParent<Table>,
        HasEnabled,
        HasName,
        HasMainInterface,
        HasMutator<ForeignKeyMutator> {

    final String FOREIGN_KEY_COLUMNS = "foreignKeyColumns";

    default Stream<? extends ForeignKeyColumn> foreignKeyColumns() {
        return children(FOREIGN_KEY_COLUMNS, foreignKeyColumnConstructor());
    }

    default ForeignKeyColumn addNewForeignKeyColumn() {
        return foreignKeyColumnConstructor().apply(this, newDocument(this, FOREIGN_KEY_COLUMNS));
    }

    BiFunction<ForeignKey, Map<String, Object>, ? extends ForeignKeyColumn> foreignKeyColumnConstructor();

    @Override
    default Class<ForeignKey> mainInterface() {
        return ForeignKey.class;
    }

    @Override
    default ForeignKeyMutator mutator() {
        return DocumentMutator.of(this);
    }
}