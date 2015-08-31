/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.code.entity;

import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.PARAM;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.RETURN;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.SEE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.STRING;
import static com.speedment.internal.codegen.util.Formatting.DOT;
import com.speedment.config.Column;
import com.speedment.config.ForeignKey;
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Project;
import com.speedment.config.Table;
import com.speedment.field.ReferenceComparableField;
import com.speedment.field.ReferenceComparableForeignKeyField;
import com.speedment.field.ReferenceComparableForeignKeyStringField;
import com.speedment.field.ReferenceComparableStringField;
import com.speedment.field.ReferenceField;
import com.speedment.field.ReferenceForeignKeyField;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.field.ReferenceComparableFieldImpl;
import com.speedment.internal.core.field.ReferenceComparableForeignKeyFieldImpl;
import com.speedment.internal.core.field.ReferenceComparableForeignKeyStringFieldImpl;
import com.speedment.internal.core.field.ReferenceComparableStringFieldImpl;
import com.speedment.internal.core.field.ReferenceFieldImpl;
import com.speedment.internal.core.field.ReferenceForeignKeyFieldImpl;
import com.speedment.internal.core.field.encoder.JsonEncoder;
import com.speedment.db.MetaResult;
import com.speedment.internal.util.Pluralis;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import com.speedment.internal.util.JavaLanguage;
import static com.speedment.internal.util.JavaLanguage.javaTypeName;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Consumer;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class EntityTranslatorSupport {

    public static final String CONSUMER_NAME = "consumer";
    public static final String FIND = "find";

    public EntityTranslatorSupport() {
        instanceNotAllowed(getClass());
    }

    public static Type getEntityType(Table table) {
        requireNonNull(table);
        final Project project = table.ancestor(Project.class).get();

        return Type.of(
            project.getPackageName().toLowerCase() + DOT
            + table.getRelativeName(Project.class) + DOT
            + javaTypeName(table.getName())
        );
    }

    public static class ReferenceFieldType {

        public final Type type, implType;

        public ReferenceFieldType(Type type, Type implType) {
            this.type = type;
            this.implType = implType;
        }
    }

    public static ReferenceFieldType getReferenceFieldType(File file, Table table, Column column, Type entityType) {
        requireNonNull(file);
        requireNonNull(table);
        requireNonNull(column);
        requireNonNull(entityType);

        final Class<?> mapping = column.getMapping();

        return EntityTranslatorSupport.getForeignKey(table, column)
            // If this is a foreign key.
            .map(fkc -> {
                final Type type, implType;
                final Type fkType = getEntityType(fkc.getForeignTable());

                file.add(Import.of(fkType));

                if (String.class.equals(mapping)) {
                    type = Type.of(ReferenceComparableForeignKeyStringField.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(fkType));

                    implType = Type.of(ReferenceComparableForeignKeyStringFieldImpl.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(fkType));
                } else if (Comparable.class.isAssignableFrom(mapping)) {
                    type = Type.of(ReferenceComparableForeignKeyField.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(Type.of(mapping)))
                        .add(Generic.of().add(fkType));

                    implType = Type.of(ReferenceComparableForeignKeyFieldImpl.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(Type.of(mapping)))
                        .add(Generic.of().add(fkType));
                } else {
                    type = Type.of(ReferenceForeignKeyField.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(Type.of(mapping)))
                        .add(Generic.of().add(fkType));

                    implType = Type.of(ReferenceForeignKeyFieldImpl.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(Type.of(mapping)))
                        .add(Generic.of().add(fkType));
                }

                return new ReferenceFieldType(type, implType);

                // If it is not a foreign key
            }).orElseGet(() -> {
            final Type type, implType;

            if (String.class.equals(mapping)) {
                type = Type.of(ReferenceComparableStringField.class)
                    .add(Generic.of().add(entityType));

                implType = Type.of(ReferenceComparableStringFieldImpl.class)
                    .add(Generic.of().add(entityType));
            } else if (Comparable.class.isAssignableFrom(mapping)) {
                type = Type.of(ReferenceComparableField.class)
                    .add(Generic.of().add(entityType))
                    .add(Generic.of().add(Type.of(mapping)));

                implType = Type.of(ReferenceComparableFieldImpl.class)
                    .add(Generic.of().add(entityType))
                    .add(Generic.of().add(Type.of(mapping)));
            } else {
                type = Type.of(ReferenceField.class)
                    .add(Generic.of().add(entityType))
                    .add(Generic.of().add(Type.of(mapping)));

                implType = Type.of(ReferenceFieldImpl.class)
                    .add(Generic.of().add(entityType))
                    .add(Generic.of().add(Type.of(mapping)));
            }

            return new ReferenceFieldType(type, implType);
        });
    }

    public static String pluralis(Table table) {
        return Pluralis.INSTANCE.pluralizeJavaIdentifier(JavaLanguage.javaTypeName(requireNonNull(table).getName()));
    }

    public static Method toJson() {
        return Method.of("toJson", STRING)
            .set(Javadoc.of(
                "Returns a JSON representation of this Entity using the default {@link JsonFormatter}. "
                + "All of the fields in this Entity will appear in the returned JSON String."
            )
                .add(RETURN.setText("Returns a JSON representation of this Entity using the default {@link JsonFormatter}"))
            );
    }

    public static Method toJsonExtended(Type entityType) {
        requireNonNull(entityType);
        final String paramName = "jsonFormatter";
        return Method.of("toJson", STRING)
            .add(Field.of(paramName, Type.of(JsonEncoder.class)
                .add(Generic.of().add(entityType))))
            .set(Javadoc.of(
                "Returns a JSON representation of this Entity using the provided {@link JsonFormatter}."
            )
                .add(PARAM.setValue(paramName).setText("to use as a formatter"))
                .add(RETURN.setText("Returns a JSON representation of this Entity using the provided {@link JsonFormatter}"))
                .add(SEE.setText("JsonFormatter"))
            );

    }

    public static Optional<ForeignKeyColumn> getForeignKey(Table table, Column column) {
        requireNonNull(table);
        requireNonNull(column);
        return table.streamOf(ForeignKey.class)
            .filter(ForeignKey::isEnabled)
            .flatMap(fk -> fk.streamOf(ForeignKeyColumn.class))
            .filter(ForeignKeyColumn::isEnabled)
            .filter(fkc -> fkc.getColumn().equals(column))
            .findFirst();
    }

    public static Method dbMethod(String name, Type entityType) {
        requireNonNull(name);
        requireNonNull(entityType);
        return Method.of(name, entityType).add(Type.of(SpeedmentException.class));
        //.add("return " + MANAGER.getName() + ".get()." + name + "(this);");
    }

    public static Method dbMethodWithListener(String name, Type entityType) {
        requireNonNull(name);
        requireNonNull(entityType);
        return Method.of(name, entityType).add(Type.of(SpeedmentException.class))
            .add(Field.of(CONSUMER_NAME, Type.of(Consumer.class).add(Generic.of().add(Type.of(MetaResult.class).add(Generic.of().add(entityType))))));
        //.add("return " + MANAGER.getName() + ".get()." + name + "(this, " + CONSUMER_NAME + ");");
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

}
