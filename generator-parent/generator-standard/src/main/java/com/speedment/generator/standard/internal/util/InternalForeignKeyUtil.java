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
package com.speedment.generator.standard.internal.util;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.util.ForeignKeyUtil;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.field.*;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public final class InternalForeignKeyUtil {

    private InternalForeignKeyUtil() {}

    public static ForeignKeyUtil.ReferenceFieldType getReferenceFieldType(
        final File file,
        final Table table,
        final Column column,
        final Type entityType,
        final Injector injector
    ) {
        requireNonNull(file);
        requireNonNull(table);
        requireNonNull(column);
        requireNonNull(entityType);
        requireNonNull(injector);

        final TypeMapper<?, ?> tm = injector.getOrThrow(TypeMapperComponent.class).get(column);
        final Type javaType       = tm.getJavaType(column);
        final Type databaseType   = SimpleType.create(column.getDatabaseType());
        final TypeMapper.Category category = tm.getJavaTypeCategory(column);

        return ForeignKeyUtil.getForeignKey(table, column)
            // If this is a foreign key.
            .map(fkc -> {
                final Type type;
                final Type fkType = new TranslatorSupport<>(injector, fkc.findForeignTable().orElseThrow(
                    () -> new SpeedmentException(
                        "Could not find referenced foreign table '"
                            + fkc.getForeignTableName() + "'."
                    ))).entityType();

                file.add(Import.of(fkType));

                switch (category) {
                    case STRING :
                        type = SimpleParameterizedType.create(
                            StringForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case BYTE :
                        type = SimpleParameterizedType.create(
                            ByteForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case SHORT :
                        type = SimpleParameterizedType.create(
                            ShortForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case INT :
                        type = SimpleParameterizedType.create(
                            IntForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case LONG :
                        type = SimpleParameterizedType.create(
                            LongForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case FLOAT :
                        type = SimpleParameterizedType.create(
                            FloatForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case DOUBLE :
                        type = SimpleParameterizedType.create(
                            DoubleForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case CHAR :
                        type = SimpleParameterizedType.create(
                            CharForeignKeyField.class,
                            entityType,
                            databaseType,
                            fkType
                        );

                        break;

                    case ENUM :
                        type = SimpleParameterizedType.create(
                            EnumForeignKeyField.class,
                            entityType,
                            databaseType,
                            javaType,
                            fkType
                        );

                        break;

                    case BOOLEAN :
                        throw new UnsupportedOperationException(
                            "Boolean foreign key fields are not supported."
                        );

                    case COMPARABLE :
                        type = SimpleParameterizedType.create(
                            ComparableForeignKeyField.class,
                            entityType,
                            databaseType,
                            javaType,
                            fkType
                        );

                        break;

                    case REFERENCE :
                        throw new UnsupportedOperationException(
                            "Foreign key types that are not either primitive " +
                                "or comparable are not supported."
                        );

                    default : throw new UnsupportedOperationException(
                        "Unknown enum constant '" + category + "'."
                    );
                }

                return new ForeignKeyUtil.ReferenceFieldType(type);

                // If it is not a foreign key
            }).orElseGet(() -> {
                final Type type;

                switch (category) {
                    case STRING :

                        type = SimpleParameterizedType.create(
                            StringField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case BYTE :
                        type = SimpleParameterizedType.create(
                            ByteField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case SHORT :
                        type = SimpleParameterizedType.create(
                            ShortField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case INT :
                        type = SimpleParameterizedType.create(
                            IntField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case LONG :
                        type = SimpleParameterizedType.create(
                            LongField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case FLOAT :
                        type = SimpleParameterizedType.create(
                            FloatField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case DOUBLE :
                        type = SimpleParameterizedType.create(
                            DoubleField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case CHAR :
                        type = SimpleParameterizedType.create(
                            CharField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case BOOLEAN :
                        type = SimpleParameterizedType.create(
                            BooleanField.class,
                            entityType,
                            databaseType
                        );

                        break;

                    case ENUM :
                        type = SimpleParameterizedType.create(
                            EnumField.class,
                            entityType,
                            databaseType,
                            javaType
                        );

                        break;

                    case COMPARABLE :
                        type = SimpleParameterizedType.create(
                            ComparableField.class,
                            entityType,
                            databaseType,
                            javaType
                        );

                        break;

                    case REFERENCE :
                        type = SimpleParameterizedType.create(
                            ReferenceField.class,
                            entityType,
                            databaseType,
                            javaType
                        );

                        break;
                    default : throw new UnsupportedOperationException(
                        "Unknown enum constant '" + category + "'."
                    );
                }

                return new ForeignKeyUtil.ReferenceFieldType(type);
            });
    }

    public static Optional<? extends ForeignKeyColumn> getForeignKey(Table table, Column column) {
        requireNonNull(table);
        requireNonNull(column);
        return table.foreignKeys()
            .filter(HasEnabled::test)
            .filter(fk -> fk.foreignKeyColumns().count() == 1) // We can only handle one column FKs...
            .flatMap(ForeignKey::foreignKeyColumns)
            .filter(fkc -> fkc.findForeignTable().map(Table::isEnabled).orElse(false)) // We can only handle FKs pointing to an enabled Table
            .filter(fkc -> fkc.findForeignColumn().map(Column::isEnabled).orElse(false)) // We can only handle FKs pointing to an enabled column
            .filter(fkc -> DocumentDbUtil.isSame(column, fkc.findColumn().orElse(null)))
            .findFirst();
    }


}
