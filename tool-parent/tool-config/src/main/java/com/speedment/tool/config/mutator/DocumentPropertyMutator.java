/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.config.mutator;


import com.speedment.tool.config.*;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
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