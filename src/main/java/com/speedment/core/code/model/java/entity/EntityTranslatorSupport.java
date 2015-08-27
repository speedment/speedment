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
package com.speedment.core.code.model.java.entity;

import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.PARAM;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.RETURN;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.SEE;
import static com.speedment.codegen.lang.models.constants.DefaultType.STRING;
import static com.speedment.codegen.lang.models.constants.DefaultType.VOID;
import static com.speedment.codegen.util.Formatting.DOT;
import com.speedment.api.config.Column;
import com.speedment.api.config.ForeignKey;
import com.speedment.api.config.ForeignKeyColumn;
import com.speedment.api.config.Project;
import com.speedment.api.config.Table;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.core.field.reference.ComparableReferenceField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import com.speedment.core.encoder.JsonEncoder;
import com.speedment.core.manager.metaresult.MetaResult;
import com.speedment.util.Pluralis;
import static com.speedment.util.Util.instanceNotAllowed;
import com.speedment.util.JavaLanguage;
import static com.speedment.util.JavaLanguage.javaTypeName;
import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class EntityTranslatorSupport {

    public static final String CONSUMER_NAME = "consumer";

    public EntityTranslatorSupport() {
        instanceNotAllowed(getClass());
    }

    public static Type getEntityType(Table table) {
        final Project project = table.ancestor(Project.class).get();

        return Type.of(
            project.getPackageName().toLowerCase() + DOT
            + table.getRelativeName(Project.class) + DOT
            + javaTypeName(table.getName())
        );
    }

    public static Type getReferenceFieldType(File file, Table table, Column column, Type entityType) {
        final Class<?> mapping = column.getMapping();

        return EntityTranslatorSupport.getForeignKey(table, column)
            // If this is a foreign key.
            .map(fkc -> {
                final Type t;

                final Type fkType = getEntityType(fkc.getForeignTable());

                file.add(Import.of(fkType));

                if (String.class.equals(mapping)) {
                    t = Type.of(StringReferenceForeignKeyField.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(fkType));
                } else if (Comparable.class.isAssignableFrom(mapping)) {
                    t = Type.of(ComparableReferenceForeignKeyField.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(Type.of(mapping)))
                        .add(Generic.of().add(fkType));
                } else {
                    t = Type.of(ReferenceForeignKeyField.class)
                        .add(Generic.of().add(entityType))
                        .add(Generic.of().add(Type.of(mapping)))
                        .add(Generic.of().add(fkType));
                }

                return t;

                // If it is not a foreign key
            }).orElseGet(() -> {
            if (String.class.equals(mapping)) {
                return Type.of(StringReferenceField.class)
                    .add(Generic.of().add(entityType));
            } else if (Comparable.class.isAssignableFrom(mapping)) {
                return Type.of(ComparableReferenceField.class)
                    .add(Generic.of().add(entityType))
                    .add(Generic.of().add(Type.of(mapping)));
            } else {
                return Type.of(ReferenceField.class)
                    .add(Generic.of().add(entityType))
                    .add(Generic.of().add(Type.of(mapping)));
            }
        });
    }

    public static String pluralis(Table table) {
        return Pluralis.INSTANCE.pluralizeJavaIdentifier(JavaLanguage.javaVariableName(table.getName()));
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
        return table.streamOf(ForeignKey.class)
            .filter(ForeignKey::isEnabled)
            .flatMap(fk -> fk.streamOf(ForeignKeyColumn.class))
            .filter(ForeignKeyColumn::isEnabled)
            .filter(fkc -> fkc.getColumn().equals(column))
            .findFirst();
    }

    public static Method dbMethod(String name, Type entityType) {
        return Method.of(name, entityType).add(Type.of(SpeedmentException.class));
        //.add("return " + MANAGER.getName() + ".get()." + name + "(this);");
    }

    public static Method dbMethodWithListener(String name, Type entityType) {
        return Method.of(name, entityType).add(Type.of(SpeedmentException.class))
            .add(Field.of(CONSUMER_NAME, Type.of(Consumer.class).add(Generic.of().add(Type.of(MetaResult.class).add(Generic.of().add(entityType))))));
        //.add("return " + MANAGER.getName() + ".get()." + name + "(this, " + CONSUMER_NAME + ");");
    }

    public static Method persist(Type entityType) {
        return EntityTranslatorSupport.dbMethod("persist", entityType);
    }

    public static Method update(Type entityType) {
        return EntityTranslatorSupport.dbMethod("update", entityType);

    }

    public static Method remove(Type entityType) {
        return EntityTranslatorSupport.dbMethod("remove", entityType);
    }

    public static Method persistWithListener(Type entityType) {
        return EntityTranslatorSupport.dbMethodWithListener("persist", entityType);
    }

    public static Method updateWithListener(Type entityType) {
        return EntityTranslatorSupport.dbMethodWithListener("update", entityType);
    }

    public static Method removeWithListener(Type entityType) {
        return EntityTranslatorSupport.dbMethodWithListener("remove", entityType);
    }

}
