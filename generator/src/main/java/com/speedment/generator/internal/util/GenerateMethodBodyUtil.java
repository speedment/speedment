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
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.generator.TranslatorSupport;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.exception.SpeedmentException;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.internal.util.Formatting;
import static com.speedment.common.codegen.internal.util.Formatting.*;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import static com.speedment.generator.internal.manager.GeneratedManagerImplTranslator.*;
import com.speedment.runtime.util.OptionalUtil;
import java.lang.reflect.Type;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
public final class GenerateMethodBodyUtil {
    
    public static Method generateGet(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(GET_METHOD, Object.class).public_().add(OVERRIDE)
            .add(Field.of("entity", support.entityType()))
            .add(Field.of("identifier", SimpleParameterizedType.create(FieldIdentifier.class, support.entityType())))
            .add(generateGetBody(support, file, columnsSupplier));
    }

    public static String[] generateGetBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(IllegalArgumentException.class));

        return new String[]{
            "switch ((" + support.entityName() + ".Identifier) identifier) " + block(
            columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .map(c
            -> "case " + support.namer().javaStaticFieldName(c.getJavaName())
            + " : return " + getterCode(file, support, c)
            + ";"
            ).collect(Collectors.joining(nl()))
            + nl() + "default : throw new IllegalArgumentException(\"Unknown identifier '\" + identifier + \"'.\");"
            )
        };
    }

    public static Method generateSet(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(SET_METHOD, void.class).public_().add(OVERRIDE)
            .add(Field.of("entity", support.entityType()))
            .add(Field.of("identifier", SimpleParameterizedType.create(FieldIdentifier.class, support.entityType())))
            .add(Field.of("value", Object.class))
            .add(generateSetBody(support, file, columnsSupplier));
    }

    public static String[] generateSetBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(IllegalArgumentException.class));
        
        return new String[]{
            "switch ((" + support.entityName() + ".Identifier) identifier) " + block(columnsSupplier.get()
                .filter(HasEnabled::isEnabled)
                .peek(c -> file.add(Import.of(support.typeOf(c))))
                .map(c -> 
                    "case " + support.namer().javaStaticFieldName(c.getJavaName())
                    + " : entity." + SETTER_METHOD_PREFIX + support.typeName(c)
                    + "("
                    + castToColumnTypeIfNotObject(support, file, c)
                    + "value); break;"
                ).collect(Collectors.joining(nl()))
                    + nl() + "default : throw new IllegalArgumentException(\"Unknown identifier '\" + identifier + \"'.\");"
            )
        };
    }

    public static Method generateFields(TranslatorSupport<Table> support, File file, String methodName, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(methodName, 
                DefaultType.stream(
                    SimpleParameterizedType.create(
                        com.speedment.runtime.field.Field.class,
                        support.entityType()
                    )
                )
            )
            .public_().add(OVERRIDE)
            .add(generateFieldsBody(support, file, columnsSupplier));
    }

    public static String[] generateFieldsBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(Stream.class));
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
    
    private static String castToColumnTypeIfNotObject(TranslatorSupport<Table> support, File file, Column c) {
        final Type type = support.typeOf(c);
        if (type.equals(Object.class)) {
            return "";
        } else {
            file.add(Import.of(type));
            return "(" + Formatting.shortName(type.getTypeName()) + ") ";
        }
    }
    
    private static String getterCode(File file, TranslatorSupport<Table> support, Column c) {
        if (c.isNullable()) {
            file.add(Import.of(OptionalUtil.class));
            return "OptionalUtil.unwrap(entity." + GETTER_METHOD_PREFIX + support.typeName(c) + "())";
        } else {
            return "entity." + GETTER_METHOD_PREFIX + support.typeName(c) + "()";
        }
    }
    
    private GenerateMethodBodyUtil() {}
}
