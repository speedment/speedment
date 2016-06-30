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

import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.OBJECT;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.VOID;
import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import static com.speedment.common.codegen.internal.util.Formatting.block;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import static com.speedment.common.codegen.internal.util.Formatting.nl;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.generator.TranslatorSupport;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.FIELDS_METHOD;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.GET_METHOD;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.GET_PRIMARY_KEY_CLASSES_METHOD;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.NEW_EMPTY_ENTITY_METHOD;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.NEW_ENTITY_FROM_METHOD;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.PRIMARY_KEYS_FIELDS_METHOD;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.PRIMARY_KEY_CLASSES;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.SET_METHOD;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.common.tuple.Tuples;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
public final class GenerateMethodBodyUtil {
    
    public static Method generateGet(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(GET_METHOD, OBJECT).public_().add(OVERRIDE)
            .add(Field.of("entity", support.entityType()))
            .add(Field.of("identifier", Type.of(FieldIdentifier.class).add(Generic.of().add(support.entityType()))))
            .add(generateGetBody(support, file, columnsSupplier));
    }

    public static String[] generateGetBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));

        return new String[]{
            "switch ((" + support.entityName() + ".Identifier) identifier) " + block(
            columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .map(c
            -> "case " + support.namer().javaStaticFieldName(c.getJavaName())
            + " : return entity." + getterCode(support, c)
            + ";"
            ).collect(Collectors.joining(nl()))
            + nl() + "default : throw new IllegalArgumentException(\"Unknown identifier '\" + identifier + \"'.\");"
            )
        };
    }

    public static Method generateSet(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(SET_METHOD, VOID).public_().add(OVERRIDE)
            .add(Field.of("entity", support.entityType()))
            .add(Field.of("identifier", Type.of(FieldIdentifier.class).add(Generic.of().add(support.entityType()))))
            .add(Field.of("value", Type.of(Object.class)))
            .add(generateSetBody(support, file, columnsSupplier));
    }

    public static String[] generateSetBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));

        return new String[]{
            "switch ((" + support.entityName() + ".Identifier) identifier) " + block(
            columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .peek(c -> file.add(Import.of(Type.of(c.findTypeMapper().getJavaType()))))
            .map(c
            -> "case " + support.namer().javaStaticFieldName(c.getJavaName())
            + " : entity." + SETTER_METHOD_PREFIX + support.typeName(c)
            + "("
            + castToColumnTypeIfNotObject(c)
            + "value); break;").collect(Collectors.joining(nl()))
            + nl() + "default : throw new IllegalArgumentException(\"Unknown identifier '\" + identifier + \"'.\");"
            )
        };
    }

    public static Method generateFields(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(FIELDS_METHOD, Type.of(Stream.class).add(Generic.of().add(Type.of(FieldTrait.class))))
            .public_().add(OVERRIDE)
            .add(generateFieldsBody(support, file, columnsSupplier));
    }

    public static Method generatePrimaryKeyFields(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(PRIMARY_KEYS_FIELDS_METHOD, Type.of(Stream.class).add(Generic.of().add(Type.of(FieldTrait.class))))
            .public_().add(OVERRIDE)
            .add(generateFieldsBody(support, file, columnsSupplier));
    }

    public static String[] generateFieldsBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(Type.of(Stream.class)));
        final List<String> rows = new LinkedList<>();

        rows.add("return Stream.of(");
        rows.add(indent(columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .map(Column::getJavaName)
            .map(support.namer()::javaStaticFieldName)
            .map(field -> support.typeName() + "." + field)
            .collect(joining("," + nl()))
        ));
        rows.add(");");

        return rows.toArray(new String[rows.size()]);
    }
    
    public static Method generateNewEmptyEntity(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(NEW_EMPTY_ENTITY_METHOD, support.entityType())
            .public_().add(OVERRIDE)
            .add(generateNewEmptyEntityBody(support, file, columnsSupplier));

    }

    public static String[] generateNewEmptyEntityBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(support.entityImplType()));
        return new String[] {
            "return new " + support.entityImplName() + "();"
        };
    }
    
    @FunctionalInterface
    public interface ReadFromResultSet {
        String readFromResultSet(File file, Column c, AtomicInteger position);
    }
    // String readFromResultSet(File file, Column c, AtomicInteger position)

    public static String[] generateNewEntityFromBody(ReadFromResultSet readFromResultSet, TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {

        final List<String> rows = new LinkedList<>();
        rows.add("final " + support.entityName() + " entity = " + NEW_EMPTY_ENTITY_METHOD + "();");

        final Stream.Builder<String> streamBuilder = Stream.builder();

        final AtomicInteger position = new AtomicInteger(1);
        columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .forEachOrdered(c -> {
                streamBuilder.add("entity.set" + support.namer().javaTypeName(c.getJavaName()) + "(" + readFromResultSet.readFromResultSet(file, c, position) + ");");
            });

        rows.add("try " + block(streamBuilder.build()));
        rows.add("catch (" + SQLException.class.getSimpleName() + " sqle) " + block(
            "throw new " + SpeedmentException.class.getSimpleName() + "(sqle);"
        ));
        rows.add("return entity;");

        return rows.toArray(new String[rows.size()]);
    }
    
    private static String castToColumnTypeIfNotObject(Column c) {
        final java.lang.Class<?> castType = c.findTypeMapper().getJavaType();
        if (Object.class.equals(castType)) {
            return "";
        } else {
            return "(" + c.findTypeMapper().getJavaType().getSimpleName() + ") ";
        }
    }
    
    private static String getterCode(TranslatorSupport<Table> support, Column c) {
        if (c.isNullable()) {
            return GETTER_METHOD_PREFIX + support.typeName(c) + "().orElse(null)";
        } else {
            return GETTER_METHOD_PREFIX + support.typeName(c) + "()";
        }
    }
    
    private GenerateMethodBodyUtil() {}
}
