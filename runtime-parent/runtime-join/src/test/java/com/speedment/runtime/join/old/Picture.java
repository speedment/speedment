package com.speedment.runtime.join.old;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Per Minborg
 */
public class Picture {

    private int id;
    private int owner;

    private static final IntField<Picture, Integer> ID
        = IntField.create(Identifier.ID,
            Picture::getId,
            Picture::setId,
            TypeMapper.primitive(),
            true
        );

    private static final IntField<Picture, Integer> OWNER
        = IntField.create(Identifier.OWNER,
            Picture::getOwner,
            Picture::setOwner,
            TypeMapper.primitive(),
            true
        );

    int getId() {
        return id;
    }

    Picture setId(int id) {
        this.id = id;
        return this;
    }

    int getOwner() {
        return owner;
    }

    Picture setOwner(int owner) {
        this.owner = owner;
        return this;
    }

    enum Identifier implements ColumnIdentifier<Picture> {
        ID("id"),
        OWNER("owner");

        private final String columnName;
        private final TableIdentifier<Picture> tableIdentifier;

        Identifier(String columnName) {
            this.columnName = columnName;
            this.tableIdentifier = TableIdentifier.of(getDbmsName(),
                getSchemaName(),
                getTableName());
        }

        @Override
        public String getDbmsName() {
            return "db0";
        }

        @Override
        public String getSchemaName() {
            return "SPEEDMENT";
        }

        @Override
        public String getTableName() {
            return "PICTURE";
        }

        @Override
        public String getColumnName() {
            return this.columnName;
        }

        @Override
        public TableIdentifier<Picture> asTableIdentifier() {
            return this.tableIdentifier;
        }

    }

}
