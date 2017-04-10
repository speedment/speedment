package com.speedment.runtime.test_support;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Per Minborg
 */
public class MockEntity {

    /**
     * This Field corresponds to the {@link Country} field that can be obtained
     * using the {@link Country#getId()} method.
     */
    public static final IntField<MockEntity, Integer> ID = IntField.create(
        Identifier.ID,
        MockEntity::getId,
        MockEntity::setId,
        TypeMapper.primitive(),
        true
    );

    public static final StringField<MockEntity, String> NAME = StringField.create(
        Identifier.NAME,
        MockEntity::getName,
        MockEntity::setName,
        TypeMapper.identity(),
        true
    );

    private int id;
    private String name;

    public MockEntity(int id) {
        this.id = id;
        this.name = "Name" + id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MockEntity setId(int id) {
        this.id = id;
        return this;
    }

    public MockEntity setName(String name) {
        this.name = name;
        return this;
    }

    enum Identifier implements ColumnIdentifier<MockEntity> {

        ID("id"),
        NAME("name");

        private final String columnName;
        private final TableIdentifier<MockEntity> tableIdentifier;

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
            return "speedment_test";
        }

        @Override
        public String getTableName() {
            return "mock_entity";
        }

        @Override
        public String getColumnName() {
            return this.columnName;
        }

        @Override
        public TableIdentifier<MockEntity> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }

}
