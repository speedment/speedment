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
package com.speedment.plugins.enums.internal;

import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.value.InvocationValue;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.injector.Injector;
import com.speedment.generator.core.exception.SpeedmentGeneratorException;
import com.speedment.generator.standard.util.FkHolder;
import com.speedment.generator.standard.util.ForeignKeyUtil;
import com.speedment.generator.translator.JavaClassTranslator;
import com.speedment.generator.translator.Translator;
import com.speedment.generator.translator.TranslatorDecorator;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.plugins.enums.IntegerToEnumTypeMapper;
import com.speedment.plugins.enums.StringToEnumTypeMapper;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.field.EnumField;
import com.speedment.runtime.field.EnumForeignKeyField;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.util.*;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultType.*;
import static com.speedment.common.codegen.model.Value.*;
import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.generator.standard.util.ColumnUtil.usesOptional;
import static com.speedment.plugins.enums.internal.EnumGeneratorUtil.enumConstantsOf;
import static com.speedment.plugins.enums.internal.EnumGeneratorUtil.enumNameOf;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class GeneratedEntityDecorator implements TranslatorDecorator<Table, Interface> {

    private static final Set<String> ENUM_TYPE_MAPPERS =
        unmodifiableSet(new HashSet<>(asList(
            StringToEnumTypeMapper.class.getName(),
            IntegerToEnumTypeMapper.class.getName()
        )));

    public static final String FROM_DATABASE_METHOD         = "fromDatabase";
    public static final String FROM_DATABASE_ORDINAL_METHOD = "fromDatabaseOrdinal";
    public static final String TO_DATABASE_METHOD           = "toDatabase";
    public static final String TO_DATABASE_ORDINAL_METHOD   = "toDatabaseOrdinal";
    public static final String DATABASE_NAME                = "databaseName";
    public static final String DATABASE_ORDINAL             = "databaseOrdinal";

    private static final String COLUMN = "column";
    private static final String RETURN = "return ";

    private final Injector injector;
    
    public GeneratedEntityDecorator(Injector injector) {
        this.injector = requireNonNull(injector);
    }
    
    @Override
    public void apply(JavaClassTranslator<Table, Interface> translator) {
        translator.onMake((file, builder) ->
            builder.forEveryTable(Translator.Phase.POST_MAKE, (intrf, table) -> tableHandler(translator, file, intrf, table)
        ));
    }

    private void tableHandler(JavaClassTranslator<Table, Interface> translator, File file, Interface intrf, Table table) {
        file.getImports().removeIf(i -> i.getType().equals(StringToEnumTypeMapper.class));
        file.getImports().removeIf(i -> i.getType().equals(IntegerToEnumTypeMapper.class));

        table.columns()
            .filter(HasEnabled::test)
            .filter(col -> col.getTypeMapper()
                .filter(ENUM_TYPE_MAPPERS::contains)
                .isPresent()
            ).forEachOrdered(col -> columnHandler(translator, file, intrf, col)
        );
    }

    private void columnHandler(JavaClassTranslator<Table, Interface> translator, File file, Interface intrf, Column col) {
        final JavaLanguageNamer namer = translator.getSupport().namer();

        final String colEnumName     = enumNameOf(col, injector);
        final List<String> constants = enumConstantsOf(col);

        final Type enumType = new GeneratedEnumType(
            colEnumName,
            constants
        );

        final Type dbType;
        if (hasTypeMapper(col, StringToEnumTypeMapper.class)) {
            dbType = String.class;
        } else if (hasTypeMapper(col, IntegerToEnumTypeMapper.class)) {
            dbType = int.class;
        } else {
            throw new UnsupportedOperationException(format("Unknown enum type mapper '%s' in column '%s'.", col.getTypeMapper().orElse(null), col));
        }

        final Enum colEnum = Enum.of(shortName(colEnumName))
            .add(Field.of(DATABASE_NAME, String.class)
                .private_().final_()
            ).add(Field.of(DATABASE_ORDINAL, int.class)
                .private_().final_()
            );

        // Generate fromDatabase()-method
        final Method fromDatabase = Method.of(FROM_DATABASE_METHOD, enumType)
            .public_().static_()
            .add(Field.of(DATABASE_NAME, String.class))
            .add("if (" + DATABASE_NAME + " == null) return null;")
            .add("switch (" + DATABASE_NAME + ") {");

        final Method fromDatabaseOrdinal = Method.of(FROM_DATABASE_ORDINAL_METHOD, enumType)
            .public_().static_()
            .add(Field.of(DATABASE_ORDINAL, Integer.class))
            .add("if (" + DATABASE_ORDINAL + " == null) return null;")
            .add("switch (" + DATABASE_ORDINAL + ") {");

        // Generate enum constants
        final ListIterator<String> constantIt = constants.listIterator();
        while (constantIt.hasNext()) {
            final int ordinal     = constantIt.nextIndex();
            final String constant = constantIt.next();
            final String javaName = namer.javaStaticFieldName(constant);

            final EnumConstant enumConstant =
                EnumConstant.of(javaName)
                    .add(Value.ofText(constant))
                    .add(Value.ofNumber(ordinal));

            fromDatabase.add(Formatting.indent(
                "case \"" + constant + "\" : return " +
                    namer.javaStaticFieldName(constant) + ";"
            ));

            fromDatabaseOrdinal.add(Formatting.indent(
                "case " + ordinal + " : return " +
                    namer.javaStaticFieldName(constant) + ";"
            ));

            colEnum.add(enumConstant);
        }

        // Generate constructor
        colEnum.add(Constructor.of()
            .add(Field.of(DATABASE_NAME, String.class))
            .add(Field.of(DATABASE_ORDINAL, int.class))
            .add("this." + DATABASE_NAME + "    = " + DATABASE_NAME + ";")
            .add("this." + DATABASE_ORDINAL + " = " + DATABASE_ORDINAL + ";")
        );

        fromDatabase
            .add(Formatting.indent("default : throw new UnsupportedOperationException("))
            .add(Formatting.indent("\"Unknown enum constant '\" + " + DATABASE_NAME + " + \"'.\"", 2))
            .add(Formatting.indent(");"))
            .add("}");

        fromDatabaseOrdinal
            .add(Formatting.indent("default : throw new UnsupportedOperationException("))
            .add(Formatting.indent("\"Unknown enum ordinal '\" + " + DATABASE_ORDINAL + " + \"'.\"", 2))
            .add(Formatting.indent(");"))
            .add("}");

        colEnum.add(fromDatabase);
        colEnum.add(fromDatabaseOrdinal);

        // Generate toDatabase()-method
        colEnum.add(Method.of(TO_DATABASE_METHOD, String.class)
            .public_().add(RETURN + DATABASE_NAME + ";")
        );

        // Generate toDatabaseOrdinal()-method
        colEnum.add(Method.of(TO_DATABASE_ORDINAL_METHOD, int.class)
            .public_().add(RETURN + DATABASE_ORDINAL + ";")
        );

        // Add it to the interface.
        intrf.add(colEnum);

        // Rewrite the static field to use a inlined type mapper
        // instead.
        final String fieldName = namer.javaStaticFieldName(col.getJavaName());
        final Field field = intrf.getFields().stream()
            .filter(f -> f.getName().equals(fieldName))
            .findFirst().orElseThrow(() -> new SpeedmentGeneratorException(
                "Expected a static field with name '" +
                fieldName + "' to be generated."
            ));

        if (usesOptional(col)) {
            file.add(Import.of(OptionalUtil.class));
        }

        final String enumShortName = shortName(colEnumName);
        final String enumVarName = namer.javaVariableName(enumShortName);

        final List<Value<?>> params = new LinkedList<>();
        params.addAll(asList(
            ofReference("Identifier." + fieldName),
            ofReference(
                usesOptional(col)
                    ? "o -> " + OptionalUtil.class.getSimpleName() + ".unwrap(o.get" + translator.getSupport().typeName(col) + "())"
                    : translator.getSupport().entityName() + "::get" + translator.getSupport().typeName(col)
            ),
            ofReference(
                translator.getSupport().entityName() + "::set" +
                translator.getSupport().typeName(col)
            ),
            ofAnonymous(TypeMapper.class)
                .add(wrapIfPrimitive(dbType))
                .add(enumType)
                .add(Method.of("getLabel", String.class)
                    .public_().add(OVERRIDE)
                    .add("return \"" + shortName(dbType.getTypeName()) +
                        " to " + enumShortName + " Mapper\";")
                )
                .add(Method.of("getOrdering", TypeMapper.Ordering.class)
                    .public_().add(OVERRIDE)
                    .add(RETURN + TypeMapper.Ordering.class.getSimpleName() +
                        "." + TypeMapper.Ordering.RETAIN.name() + ";")
                )
                .add(Method.of("getJavaTypeCategory", TypeMapper.Category.class)
                    .public_().add(OVERRIDE)
                    .add(Field.of(COLUMN, Column.class))
                    .add(RETURN + TypeMapper.Category.class.getSimpleName() +
                        "." + TypeMapper.Category.ENUM.name() + ";")
                )
                .add(Method.of("getJavaType", Type.class)
                    .public_().add(OVERRIDE)
                    .add(Field.of(COLUMN, Column.class))
                    .add(RETURN + enumShortName + ".class;")
                )
                .add(Method.of("toJavaType", enumType)
                    .public_().add(OVERRIDE)
                    .add(Field.of(COLUMN, Column.class))
                    .add(Field.of("clazz", classOf(WILDCARD)))
                    .add(Field.of("value", wrapIfPrimitive(dbType)))
                    .add("return value == null ? null : " +
                        enumShortName + "." + ((dbType == String.class)
                            ? FROM_DATABASE_METHOD
                            : FROM_DATABASE_ORDINAL_METHOD
                        ) + "(value);"
                    )
                )
                .add(Method.of("toDatabaseType", wrapIfPrimitive(dbType))
                    .public_().add(OVERRIDE)
                    .add(Field.of(enumVarName, enumType))
                    .add(RETURN + enumVarName + " == null ? null : " +
                        enumVarName + "." + ((dbType == String.class)
                            ? TO_DATABASE_METHOD
                            : TO_DATABASE_ORDINAL_METHOD
                        ) + "();")
                )
        ));

        // Foreign key args
        final Optional<FkHolder> fk =
            ForeignKeyUtil.getForeignKey(
                translator.getSupport().tableOrThrow(), col
            ).map(fkc -> new FkHolder(injector, fkc.getParentOrThrow()));

        fk.ifPresent(holder ->
            params.add(ofReference(
                holder.getForeignEmt().getSupport().entityName() + "." +
                translator.getSupport().namer().javaStaticFieldName(
                    holder.getForeignColumn().getJavaName()
                ) + ","
            ))
        );

        params.add(Value.ofReference(enumShortName + "::" + TO_DATABASE_METHOD));
        params.add(Value.ofReference(enumShortName + "::" + FROM_DATABASE_METHOD));
        params.add(Value.ofReference(enumShortName + ".class"));

        final Optional<InvocationValue> existing =
            field.getValue()
                .filter(InvocationValue.class::isInstance)
                .map(InvocationValue.class::cast);

        field.set(ofInvocation(
            existing.map(InvocationValue::getType).orElseGet(
                () -> fk.isPresent()
                    ? EnumForeignKeyField.class
                    : EnumField.class
            ),
            existing.map(InvocationValue::getValue)
                .orElse("create"),
            params.toArray(new Value<?>[params.size()])
        ));
    }

    private boolean hasTypeMapper(Column col, java.lang.Class<?> typeMapperClass) {
        return col.getTypeMapper()
            .filter(typeMapperClass.getName()::equals)
            .isPresent();
    }

    private Type wrapIfPrimitive(Type type) {
        return isPrimitive(type) ? wrapperFor(type) : type;
    }
}