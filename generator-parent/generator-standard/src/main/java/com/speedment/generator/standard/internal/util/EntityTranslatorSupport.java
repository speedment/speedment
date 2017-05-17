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
package com.speedment.generator.standard.internal.util;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.field.*;
import com.speedment.runtime.field.internal.*;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.util.Optional;

import static com.speedment.common.codegen.internal.util.StaticClassUtil.instanceNotAllowed;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class EntityTranslatorSupport {
    
    public static class ReferenceFieldType {

        private final Type type, implType;

        private ReferenceFieldType(Type type, Type implType) {
            this.type     = requireNonNull(type);
            this.implType = requireNonNull(implType);
        }

        public Type getType() {
            return type;
        }

        public Type getImplType() {
            return implType;
        }
    }

    public static ReferenceFieldType getReferenceFieldType(
            File file,
            Table table,
            Column column,
            Type entityType,
            Injector injector) {
        
        requireNonNulls(file, table, column, entityType, injector);

        final TypeMapper<?, ?> tm = injector.getOrThrow(TypeMapperComponent.class).get(column);
        final Type javaType       = tm.getJavaType(column);
        final Type databaseType   = SimpleType.create(column.getDatabaseType());
        final TypeMapper.Category category = tm.getJavaTypeCategory(column);

        return EntityTranslatorSupport.getForeignKey(table, column)
            // If this is a foreign key.
            .map(fkc -> {
                final Type type, implType;
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

                        implType = SimpleParameterizedType.create(
                            StringForeignKeyFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            ByteForeignKeyFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            ShortForeignKeyFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            IntForeignKeyFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            LongForeignKeyFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            FloatForeignKeyFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            DoubleForeignKeyFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            CharForeignKeyFieldImpl.class, 
                            entityType,
                            databaseType,
                            fkType
                        );
                        
                        break;
                        
                    case BOOLEAN :
                        throw new UnsupportedOperationException(
                            "Boolean foreign key fields are not supported."
                        );
                        
                    case COMPARABLE :
                    case ENUM :
                        type = SimpleParameterizedType.create(
                            ComparableForeignKeyField.class,
                            entityType,
                            databaseType,
                            javaType,
                            fkType
                        );

                        implType = SimpleParameterizedType.create(
                            ComparableForeignKeyFieldImpl.class,
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

                return new ReferenceFieldType(type, implType);

                // If it is not a foreign key
            }).orElseGet(() -> {
                final Type type, implType;

                switch (category) {
                    case STRING :
                        
                        type = SimpleParameterizedType.create(
                            StringField.class, 
                            entityType,
                            databaseType
                        );

                        implType = SimpleParameterizedType.create(
                            StringFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            ByteFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            ShortFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            IntFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            LongFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            FloatFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            DoubleFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            CharFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            BooleanFieldImpl.class, 
                            entityType,
                            databaseType
                        );
                        
                        break;
                        
                    case COMPARABLE :
                    case ENUM :
                        type = SimpleParameterizedType.create(
                            ComparableField.class, 
                            entityType,
                            databaseType,
                            javaType
                        );

                        implType = SimpleParameterizedType.create(
                            ComparableFieldImpl.class, 
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

                        implType = SimpleParameterizedType.create(
                            ReferenceFieldImpl.class, 
                            entityType,
                            databaseType,
                            javaType
                        );
                        
                        
                        break;
                    default : throw new UnsupportedOperationException(
                        "Unknown enum constant '" + category + "'."
                    );
                }

                return new ReferenceFieldType(type, implType);
            });
    }

    public static Optional<? extends ForeignKeyColumn> getForeignKey(Table table, Column column) {
        requireNonNulls(table, column);
        return table.foreignKeys()
            .filter(HasEnabled::test)
            .flatMap(ForeignKey::foreignKeyColumns)
            .filter(fkc -> DocumentDbUtil.isSame(column, fkc.findColumn().orElse(null)))
            .findFirst();
    }

    private EntityTranslatorSupport() {
        instanceNotAllowed(getClass());
    }
}
