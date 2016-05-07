/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui.config.mutator;

import com.speedment.internal.ui.config.ColumnProperty;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.config.ForeignKeyColumnProperty;
import com.speedment.internal.ui.config.ForeignKeyProperty;
import com.speedment.internal.ui.config.IndexColumnProperty;
import com.speedment.internal.ui.config.IndexProperty;
import com.speedment.internal.ui.config.PrimaryKeyColumnProperty;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.config.SchemaProperty;
import com.speedment.internal.ui.config.TableProperty;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentPropertyMutator {

    public static ColumnPropertyMutator of(ColumnProperty column) {
        return new ColumnPropertyMutator(column);
    }

    public static DbmsPropertyMutator of(DbmsProperty column) {
        return new DbmsPropertyMutator(column);
    }

    public static ForeignKeyColumnPropertyMutator of(ForeignKeyColumnProperty fkcolumn) {
        return new ForeignKeyColumnPropertyMutator(fkcolumn);
    }

    public static ForeignKeyPropertyMutator of(ForeignKeyProperty fk) {
        return new ForeignKeyPropertyMutator(fk);
    }

    public static IndexColumnPropertyMutator of(IndexColumnProperty indexColumn) {
        return new IndexColumnPropertyMutator(indexColumn);
    }

    public static IndexPropertyMutator of(IndexProperty index) {
        return new IndexPropertyMutator(index);
    }

    public static PrimaryKeyColumnPropertyMutator of(PrimaryKeyColumnProperty pkColumn) {
        return new PrimaryKeyColumnPropertyMutator(pkColumn);
    }

    public static ProjectPropertyMutator of(ProjectProperty project) {
        return new ProjectPropertyMutator(project);
    }

    public static SchemaPropertyMutator of(SchemaProperty schema) {
        return new SchemaPropertyMutator(schema);
    }

    public static TablePropertyMutator of(TableProperty table) {
        return new TablePropertyMutator(table);
    }
    
    private DocumentPropertyMutator() {}
}