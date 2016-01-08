package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;

/**
 *
 * @author Per Minborg
 */
public interface DocumentMutator {

    void put(String key, Object value);

    
    static ColumnMutator of(Column column) {
        return new ColumnMutator(column);
    }

    static DbmsMutator of(Dbms column) {
        return new DbmsMutator(column);
    }

    static ForeignKeyColumnMutator of(ForeignKeyColumn fkcolumn) {
        return new ForeignKeyColumnMutator(fkcolumn);
    }

    static ForeignKeyMutator of(ForeignKey fk) {
        return new ForeignKeyMutator(fk);
    }

    static IndexColumnMutator of(IndexColumn indexColumn) {
        return new IndexColumnMutator(indexColumn);
    }

    static IndexMutator of(Index index) {
        return new IndexMutator(index);
    }

    static PrimaryKeyColumnMutator of(PrimaryKeyColumn pkColumn) {
        return new PrimaryKeyColumnMutator(pkColumn);
    }

    static ProjectMutator of(Project project) {
        return new ProjectMutator(project);
    }

    static SchemaMutator of(Schema schema) {
        return new SchemaMutator(schema);
    }

    static TableMutator of(Table table) {
        return new TableMutator(table);
    }

    
}
