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

import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.generator.util.JavaLanguageNamer;
import com.speedment.generator.util.Pluralis;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.db.MetaResult;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.ComparableForeignKeyField;
import com.speedment.runtime.field.ReferenceForeignKeyField;
import com.speedment.runtime.field.StringForeignKeyField;
import com.speedment.runtime.internal.field.ReferenceForeignKeyFieldImpl;
import com.speedment.runtime.internal.field.StringForeignKeyFieldImpl;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;

import java.util.Optional;
import java.util.function.Consumer;

import static com.speedment.common.codegen.internal.util.StaticClassUtil.instanceNotAllowed;
import com.speedment.common.injector.Injector;
import com.speedment.generator.TranslatorSupport;
import com.speedment.generator.typetoken.TypeTokenGenerator;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.BooleanForeignKeyField;
import com.speedment.runtime.field.ByteField;
import com.speedment.runtime.field.ByteForeignKeyField;
import com.speedment.runtime.field.CharField;
import com.speedment.runtime.field.CharForeignKeyField;
import com.speedment.runtime.field.ComparableField;
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
import com.speedment.runtime.internal.field.BooleanFieldImpl;
import com.speedment.runtime.internal.field.BooleanForeignKeyFieldImpl;
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
import com.speedment.runtime.util.TypeTokenFactory;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
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

        final TypeToken mapping = injector.getOrThrow(TypeTokenGenerator.class).tokenOf(column);
        final Type databaseType = Type.of(column.getDatabaseType());
        
        final FieldType fieldType;
        if (TypeTokenFactory.createStringToken().equals(mapping)) {
            fieldType = FieldType.STRING;
        } else if (TypeTokenFactory.create(byte.class).equals(mapping)) {
            fieldType = FieldType.BYTE;
        } else if (TypeTokenFactory.create(short.class).equals(mapping)) {
            fieldType = FieldType.SHORT;
        } else if (TypeTokenFactory.create(int.class).equals(mapping)) {
            fieldType = FieldType.INT;
        } else if (TypeTokenFactory.create(long.class).equals(mapping)) {
            fieldType = FieldType.LONG;
        } else if (TypeTokenFactory.create(float.class).equals(mapping)) {
            fieldType = FieldType.FLOAT;
        } else if (TypeTokenFactory.create(double.class).equals(mapping)) {
            fieldType = FieldType.DOUBLE;
        } else if (TypeTokenFactory.create(char.class).equals(mapping)) {
            fieldType = FieldType.CHAR;
        } else if (TypeTokenFactory.create(boolean.class).equals(mapping)) {
            fieldType = FieldType.BOOLEAN;
        } else if (mapping.isComparable()) {
            fieldType = FieldType.COMPARABLE;
        } else {
            fieldType = FieldType.REFERENCE;
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
                        type = Type.of(StringForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(StringForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case BYTE :
                        type = Type.of(ByteForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(ByteForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case SHORT :
                        type = Type.of(ShortForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(ShortForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case INT :
                        type = Type.of(IntForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(IntForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case LONG :
                        type = Type.of(LongForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(LongForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case FLOAT :
                        type = Type.of(FloatForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(FloatForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case DOUBLE :
                        type = Type.of(DoubleForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(DoubleForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case CHAR :
                        type = Type.of(CharForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(CharForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case BOOLEAN :
                        type = Type.of(BooleanForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));

                        implType = Type.of(BooleanForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(fkType));
                        break;
                        
                    case COMPARABLE :
                        type = Type.of(ComparableForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)))
                            .add(Generic.of(fkType));

                        implType = Type.of(ComparableForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)))
                            .add(Generic.of(fkType));
                        break;
                        
                    case REFERENCE :
                        type = Type.of(ReferenceForeignKeyField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)))
                            .add(Generic.of(fkType));

                        implType = Type.of(ReferenceForeignKeyFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)))
                            .add(Generic.of(fkType));
                        
                        break;
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
                        type = Type.of(StringField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(StringFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case BYTE :
                        type = Type.of(ByteField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(ByteFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case SHORT :
                        type = Type.of(ShortField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(ShortFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case INT :
                        type = Type.of(IntField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(IntFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case LONG :
                        type = Type.of(LongField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(LongFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case FLOAT :
                        type = Type.of(FloatField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(FloatFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case DOUBLE :
                        type = Type.of(DoubleField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(DoubleFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case CHAR :
                        type = Type.of(CharField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(CharFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case BOOLEAN :
                        type = Type.of(BooleanField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));

                        implType = Type.of(BooleanFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType));
                        break;
                        
                    case COMPARABLE :
                        type = Type.of(ComparableField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)));

                        implType = Type.of(ComparableFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)));
                        break;
                        
                    case REFERENCE :
                        type = Type.of(ReferenceField.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)));

                        implType = Type.of(ReferenceFieldImpl.class)
                            .add(Generic.of(entityType))
                            .add(Generic.of(databaseType))
                            .add(Generic.of(InternalTypeTokenUtil.toType(mapping)));
                        
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
        return Method.of(name, entityType).add(Type.of(SpeedmentException.class));
    }

    public static Method dbMethodWithListener(String name, Type entityType) {
        requireNonNulls(name, entityType);
        return Method.of(name, entityType).add(Type.of(SpeedmentException.class))
            .add(Field.of(CONSUMER_NAME, Type.of(Consumer.class)
                .add(Generic.of().add(Type.of(MetaResult.class).add(Generic.of().add(entityType))))
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
