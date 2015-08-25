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
package com.speedment.core.code.model.java.entity;

import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Type;
import com.speedment.core.code.model.java.manager.EntityManagerTranslator;
import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.ForeignKey;
import com.speedment.core.config.model.ForeignKeyColumn;
import com.speedment.core.config.model.Table;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
class FkHolder {

    private final ForeignKey fk;
    private final ForeignKeyColumn fkc;
    private final Column column;
    private final Table table;
    private final Column foreignColumn;
    private final Table foreignTable;
    private final EntityManagerTranslator emt;
    private final EntityManagerTranslator foreignEmt;

    public FkHolder(Generator generator, ForeignKey fk) {
        this.fk = fk;
        fkc = fk.stream()
                .filter(ForeignKeyColumn::isEnabled)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("FK " + fk.getName() + " does not have an enabled ForeignKeyColumn"));
        column = fkc.getColumn();
        table = column.ancestor(Table.class).get();
        foreignColumn = fkc.getForeignColumn();
        foreignTable = fkc.getForeignTable();
        emt = new EntityManagerTranslator(generator, getTable());
        foreignEmt = new EntityManagerTranslator(generator, getForeignTable());
    }

    public Stream<Import> imports() {
        final Stream.Builder<Type> sb = Stream.builder();
//            sb.add(Type.of(Platform.class));
//            sb.add(Type.of(ManagerComponent.class));
//            sb.add(Type.of(Objects.class));
//            sb.add(getEmt().ENTITY.getType());
//            sb.add(getEmt().MANAGER.getType());
//            sb.add(getForeignEmt().ENTITY.getType());
//            sb.add(getForeignEmt().MANAGER.getType());
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
