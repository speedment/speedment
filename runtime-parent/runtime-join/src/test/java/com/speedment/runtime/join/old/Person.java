package com.speedment.runtime.join.old;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Per Minborg
 */
public class Person {

    private int id;
    private int age;

    private static final IntField<Person, Integer> ID
        = IntField.create(
            Identifier.ID,
            Person::getId,
            Person::setId,
            TypeMapper.primitive(),
            true
        );

    private static final IntField<Person, Integer> AGE
        = IntField.create(
            Identifier.AGE,
            Person::getAge,
            Person::setAge,
            TypeMapper.primitive(),
            true
        );

    int getId() {
        return id;
    }

    Person setId(int id) {
        this.id = id;
        return this;
    }

    int getAge() {
        return age;
    }

    Person setAge(int age) {
        this.age = age;
        return this;
    }

    enum Identifier implements ColumnIdentifier<Person> {
        ID("id"),
        AGE("age");

        private final String columnName;
        private final TableIdentifier<Person> tableIdentifier;

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
            return "PERSON";
        }

        @Override
        public String getColumnName() {
            return this.columnName;
        }

        @Override
        public TableIdentifier<Person> asTableIdentifier() {
            return this.tableIdentifier;
        }

    }

}
