/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.mutator;


import com.speedment.runtime.config.*;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */

public interface DocumentMutator<DOC extends Document> {
    
    DOC document();

    void put(String key, Object value);

    static ColumnMutator<Column> of(Column column) {
        return new ColumnMutator<>(column);
    }

    static DbmsMutator<Dbms> of(Dbms column) {
        return new DbmsMutator<>(column);
    }

    static ForeignKeyColumnMutator<ForeignKeyColumn> of(ForeignKeyColumn fkcolumn) {
        return new ForeignKeyColumnMutator<>(fkcolumn);
    }

    static ForeignKeyMutator<ForeignKey> of(ForeignKey fk) {
        return new ForeignKeyMutator<>(fk);
    }

    static IndexColumnMutator<IndexColumn> of(IndexColumn indexColumn) {
        return new IndexColumnMutator<>(indexColumn);
    }

    static IndexMutator<Index> of(Index index) {
        return new IndexMutator<>(index);
    }

    static PrimaryKeyColumnMutator<PrimaryKeyColumn> of(PrimaryKeyColumn pkColumn) {
        return new PrimaryKeyColumnMutator<>(pkColumn);
    }

    static ProjectMutator<Project> of(Project project) {
        return new ProjectMutator<>(project);
    }

    static SchemaMutator<Schema> of(Schema schema) {
        return new SchemaMutator<>(schema);
    }

    static TableMutator<Table> of(Table table) {
        return new TableMutator<>(table);
    }
}