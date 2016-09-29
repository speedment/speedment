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
package com.speedment.generator.internal.util;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.injector.Injector;
import com.speedment.generator.component.TypeMapperComponent;
import com.speedment.generator.namer.JavaLanguageNamer;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.generator.util.Pluralis;
import com.speedment.common.dbmodel.Column;
import com.speedment.common.dbmodel.ForeignKey;
import com.speedment.common.dbmodel.ForeignKeyColumn;
import com.speedment.common.dbmodel.Table;
import com.speedment.common.dbmodel.trait.HasEnabled;
import com.speedment.runtime.db.MetaResult;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.ByteField;
import com.speedment.runtime.field.ByteForeignKeyField;
import com.speedment.runtime.field.CharField;
import com.speedment.runtime.field.CharForeignKeyField;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.ComparableForeignKeyField;
import com.speedment.runtime.field.DoubleField;
import com.speedment.runtime.field.DoubleForeignKeyField;
import com.speedment.runtime.field.FloatField;
import com.speedment.runtime.field.FloatForeignKeyField;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.IntForeignKeyField;
import com.speedment.runtime.field.LongField;
import com.speedment.runtime.field.LongForeignKeyField;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.ShortField;
import com.speedment.runtime.field.ShortForeignKeyField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.StringForeignKeyField;
import com.speedment.runtime.internal.field.BooleanFieldImpl;
import com.speedment.runtime.internal.field.ByteFieldImpl;
import com.speedment.runtime.internal.field.ByteForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.CharFieldImpl;
import com.speedment.runtime.internal.field.CharForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.ComparableFieldImpl;
import com.speedment.runtime.internal.field.ComparableForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.DoubleFieldImpl;
import com.speedment.runtime.internal.field.DoubleForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.FloatFieldImpl;
import com.speedment.runtime.internal.field.FloatForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.IntFieldImpl;
import com.speedment.runtime.internal.field.IntForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.LongFieldImpl;
import com.speedment.runtime.internal.field.LongForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.ReferenceFieldImpl;
import com.speedment.runtime.internal.field.ShortFieldImpl;
import com.speedment.runtime.internal.field.ShortForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.StringFieldImpl;
import com.speedment.runtime.internal.field.StringForeignKeyFieldImpl;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Consumer;

import static com.speedment.common.codegen.internal.util.StaticClassUtil.instanceNotAllowed;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import com.speedment.common.dbmodel.util.DocumentDbUtil;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class EntityTranslatorSupport {

    public static final String CONSUMER_NAME = "consumer";
    public static final String FIND          = "find";
    
    private enum FieldType {
        STRING,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        CHAR,
        BOOLEAN,
        COMPARABLE,
        REFERENCE
    }
    
    public static class ReferenceFieldType {

        public final Type type, implType;

        public ReferenceFieldType(Type type, Type implType) {
            this.type     = requireNonNull(type);
            this.implType = requireNonNull(implType);
        }
    }

    public static ReferenceFieldType getReferenceFieldType(
            File file,
            Table table,
            Column column,
            Type entityType,
            Injector injector) {
        
        requireNonNulls(file, table, column, entityType, injector);

        final Type mapping = injector.getOrThrow(TypeMapperComponent.class).get(column).getJavaType(column);
        final Type databaseType = SimpleType.create(column.getDatabaseType());
        
        final FieldType fieldType;
        if (mapping.equals(String.class)) {
            fieldType = FieldType.STRING;
        } else if (mapping.equals(byte.class)) {
            fieldType = FieldType.BYTE;
        } else if (mapping.equals(short.class)) {
            fieldType = FieldType.SHORT;
        } else if (mapping.equals(int.class)) {
            fieldType = FieldType.INT;
        } else if (mapping.equals(long.class)) {
            fieldType = FieldType.LONG;
        } else if (mapping.equals(float.class)) {
            fieldType = FieldType.FLOAT;
        } else if (mapping.equals(double.class)) {
            fieldType = FieldType.DOUBLE;
        } else if (mapping.equals(char.class)) {
            fieldType = FieldType.CHAR;
        } else if (mapping.equals(boolean.class)) {
            fieldType = FieldType.BOOLEAN;
        } else {
            if (mapping instanceof Class<?>) {
                final Class<?> casted = (Class<?>) mapping;
                if (Comparable.class.isAssignableFrom(casted)) {
                    fieldType = FieldType.COMPARABLE;
                } else {
                    fieldType = FieldType.REFERENCE;
                }
            } else {
                fieldType = FieldType.REFERENCE;
            }
        }

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

                switch (fieldType) {
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
                        type = SimpleParameterizedType.create(
                            ComparableForeignKeyField.class,
                            entityType,
                            databaseType,
                            mapping,
                            fkType
                        );

                        implType = SimpleParameterizedType.create(
                            ComparableForeignKeyFieldImpl.class,
                            entityType,
                            databaseType,
                            mapping,
                            fkType
                        );
                        
                        break;
                        
                    case REFERENCE :
                        throw new UnsupportedOperationException(
                            "Foreign key types that are not either primitive " + 
                            "or comparable are not supported."
                        );
                    default : throw new UnsupportedOperationException(
                        "Unknown enum constant '" + fieldType + "'."
                    );
                }

                return new ReferenceFieldType(type, implType);

                // If it is not a foreign key
            }).orElseGet(() -> {
                final Type type, implType;

                switch (fieldType) {
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
                        type = SimpleParameterizedType.create(
                            ComparableField.class, 
                            entityType,
                            databaseType,
                            mapping
                        );

                        implType = SimpleParameterizedType.create(
                            ComparableFieldImpl.class, 
                            entityType,
                            databaseType,
                            mapping
                        );
                        
                        break;
                        
                    case REFERENCE :
                        type = SimpleParameterizedType.create(
                            ReferenceField.class, 
                            entityType,
                            databaseType,
                            mapping
                        );

                        implType = SimpleParameterizedType.create(
                            ReferenceFieldImpl.class, 
                            entityType,
                            databaseType,
                            mapping
                        );
                        
                        
                        break;
                    default : throw new UnsupportedOperationException(
                        "Unknown enum constant '" + fieldType + "'."
                    );
                }

                return new ReferenceFieldType(type, implType);
            });
    }

    public static String pluralis(Table table, JavaLanguageNamer javaLanguageNamer) {
        requireNonNull(table);
        return Pluralis.INSTANCE.pluralizeJavaIdentifier(javaLanguageNamer.javaTypeName(table.getJavaName()), javaLanguageNamer);
    }

    public static Optional<? extends ForeignKeyColumn> getForeignKey(Table table, Column column) {
        requireNonNulls(table, column);
        return table.foreignKeys()
            .filter(HasEnabled::test)
            .flatMap(ForeignKey::foreignKeyColumns)
            .filter(fkc -> DocumentDbUtil.isSame(column, fkc.findColumn().orElse(null)))
            .findFirst();
    }

    public static Method dbMethod(String name, Type entityType) {
        requireNonNulls(name, entityType);
        return Method.of(name, entityType).add(SpeedmentException.class);
    }

    public static Method dbMethodWithListener(String name, Type entityType) {
        requireNonNulls(name, entityType);
        return Method.of(name, entityType).add(SpeedmentException.class)
            .add(Field.of(
                CONSUMER_NAME, 
                SimpleParameterizedType.create(Consumer.class,
                    SimpleParameterizedType.create(MetaResult.class, entityType)
                )
            ));
    }

    public static Method persist(Type entityType) {
        return EntityTranslatorSupport.dbMethod("persist", requireNonNull(entityType));
    }

    public static Method update(Type entityType) {
        return EntityTranslatorSupport.dbMethod("update", requireNonNull(entityType));
    }

    public static Method remove(Type entityType) {
        return EntityTranslatorSupport.dbMethod("remove", requireNonNull(entityType));
    }

    public static Method persistWithListener(Type entityType) {
        return EntityTranslatorSupport.dbMethodWithListener("persist", requireNonNull(entityType));
    }

    public static Method updateWithListener(Type entityType) {
        return EntityTranslatorSupport.dbMethodWithListener("update", requireNonNull(entityType));
    }

    public static Method removeWithListener(Type entityType) {
        return EntityTranslatorSupport.dbMethodWithListener("remove", requireNonNull(entityType));
    }
    
    private EntityTranslatorSupport() {
        instanceNotAllowed(getClass());
    }
}
