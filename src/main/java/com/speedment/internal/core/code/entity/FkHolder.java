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
package com.speedment.internal.core.code.entity;

import com.speedment.Speedment;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.core.code.manager.EntityManagerTranslator;
import com.speedment.config.Column;
import com.speedment.config.ForeignKey;
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Table;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
final class FkHolder {

    private final ForeignKey fk;
    private final ForeignKeyColumn fkc;
    private final Column column;
    private final Table table;
    private final Column foreignColumn;
    private final Table foreignTable;
    private final EntityManagerTranslator emt;
    private final EntityManagerTranslator foreignEmt;

    FkHolder(Speedment speedment, Generator generator, ForeignKey fk) {
        requireNonNull(speedment);
        requireNonNull(generator);
        this.fk = requireNonNull(fk);
        fkc = fk.stream()
            .filter(ForeignKeyColumn::isEnabled)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("FK " + fk.getName() + " does not have an enabled ForeignKeyColumn"));
        column = fkc.getColumn();
        table = column.ancestor(Table.class).get();
        foreignColumn = fkc.getForeignColumn();
        foreignTable = fkc.getForeignTable();
        emt = new EntityManagerTranslator(speedment, generator, getTable());
        foreignEmt = new EntityManagerTranslator(speedment, generator, getForeignTable());
    }

    public Stream<Import> imports() {
        final Stream.Builder<Type> sb = Stream.builder();

        return sb.build().map(t -> Import.of(t));
    }

    public Column getColumn() {
        return column;
    }

    public Table getTable() {
        return table;
    }

    public Column getForeignColumn() {
        return foreignColumn;
    }

    public Table getForeignTable() {
        return foreignTable;
    }

    public EntityManagerTranslator getEmt() {
        return emt;
    }

    public EntityManagerTranslator getForeignEmt() {
        return foreignEmt;
    }

}
