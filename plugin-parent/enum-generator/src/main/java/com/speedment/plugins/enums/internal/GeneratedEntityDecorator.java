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
package com.speedment.plugins.enums.internal;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.injector.Injector;
import com.speedment.generator.core.exception.SpeedmentGeneratorException;
import com.speedment.generator.standard.internal.util.EntityTranslatorSupport;
import com.speedment.generator.standard.internal.util.FkHolder;
import com.speedment.generator.translator.JavaClassTranslator;
import com.speedment.generator.translator.Translator;
import com.speedment.generator.translator.TranslatorDecorator;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.plugins.enums.StringToEnumTypeMapper;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.field.EnumField;
import com.speedment.runtime.field.EnumForeignKeyField;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultType.WILDCARD;
import static com.speedment.common.codegen.constant.DefaultType.classOf;
import static com.speedment.common.codegen.model.Value.*;
import static com.speedment.common.codegen.util.Formatting.indent;
import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.generator.standard.internal.util.ColumnUtil.usesOptional;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class GeneratedEntityDecorator
implements TranslatorDecorator<Table, Interface> {
    
    public final static String
        FROM_DATABASE_METHOD = "fromDatabase",
        TO_DATABASE_METHOD   = "toDatabase",
        DATABASE_NAME_FIELD  = "databaseName";
    
    private final Injector injector;
    
    public GeneratedEntityDecorator(Injector injector) {
        this.injector = requireNonNull(injector);
    }
    
    @Override
    public void apply(JavaClassTranslator<Table, Interface> translator) {
        translator.onMake((file, builder) -> {
            builder.forEveryTable(Translator.Phase.POST_MAKE, (intrf, table) -> {

                file.getImports().removeIf(i -> i.getType().equals(StringToEnumTypeMapper.class));

                table.columns()
                    .filter(HasEnabled::test)
                    .filter(col -> col.getTypeMapper()
                        .filter(StringToEnumTypeMapper.class.getName()::equals)
                        .isPresent()
                    ).forEachOrdered(col -> {
                        final JavaLanguageNamer namer = translator.getSupport().namer();
                        
                        final String colEnumName = EnumGeneratorUtil.enumNameOf(col, injector);
                        final List<String> constants = EnumGeneratorUtil.enumConstantsOf(col);
                        final Type enumType = new GeneratedEnumType(colEnumName, constants);
                        
                        final Enum colEnum = Enum.of(shortName(colEnumName))
                            .add(Field.of(DATABASE_NAME_FIELD, String.class).private_().final_());

                        // Generate enum constants
                        constants.forEach(constant -> {
                            final String javaName = namer.javaStaticFieldName(constant);
                            colEnum.add(EnumConstant.of(javaName).add(Value.ofText(constant)));
                        });
                        
                        // Generate constructor
                        colEnum.add(Constructor.of()
                            .add(Field.of(DATABASE_NAME_FIELD, String.class))
                            .add("this." + DATABASE_NAME_FIELD + " = " + DATABASE_NAME_FIELD + ";")
                        );
                        
                        // Generate fromDatabase()-method
                        final Method fromDatabase = Method.of(FROM_DATABASE_METHOD, enumType)
                            .public_().static_()
                            .add(Field.of(DATABASE_NAME_FIELD, String.class))
                            .add("switch (" + DATABASE_NAME_FIELD + ") {");
                        
                        constants.stream()
                            .map(s -> indent("case \"" + s + "\" : return " + namer.javaStaticFieldName(s) + ";"))
                            .forEachOrdered(fromDatabase::add);
                        
                        fromDatabase
                            .add(indent("default : throw new UnsupportedOperationException("))
                            .add(indent("\"Unknown enum constant '\" + " + DATABASE_NAME_FIELD + " + \"'.\"", 2))
                            .add(indent(");"))
                            .add("}");
                        
                        colEnum.add(fromDatabase);
                        
                        // Generate toDatabase()-method
                        colEnum.add(Method.of(TO_DATABASE_METHOD, String.class)
                            .public_().add("return " + DATABASE_NAME_FIELD + ";")
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
                                    ? "o -> " + OptionalUtil.class.getSimpleName() + ".unwrap(o.get" + translator.getSupport().typeName(col) + "()),"
                                    : translator.getSupport().entityName() + "::get" + translator.getSupport().typeName(col)
                            ),
                            ofReference(
                                translator.getSupport().entityName() + "::set" +
                                    translator.getSupport().typeName(col)
                            ),
                            ofAnonymous(TypeMapper.class)
                                .add(String.class)
                                .add(enumType)
                                .add(Method.of("getLabel", String.class)
                                    .public_().add(OVERRIDE)
                                    .add("return \"String to " + enumShortName + " Mapper\";")
                                )
                                .add(Method.of("getJavaTypeCategory", TypeMapper.Category.class)
                                    .public_().add(OVERRIDE)
                                    .add(Field.of("column", Column.class))
                                    .add("return " + TypeMapper.Category.class.getSimpleName() +
                                        "." + TypeMapper.Category.ENUM.name() + ";")
                                )
                                .add(Method.of("getJavaType", Type.class)
                                    .public_().add(OVERRIDE)
                                    .add(Field.of("column", Column.class))
                                    .add("return " + enumShortName + ".class;")
                                )
                                .add(Method.of("toJavaType", enumType)
                                    .public_().add(OVERRIDE)
                                    .add(Field.of("column", Column.class))
                                    .add(Field.of("clazz", classOf(WILDCARD)))
                                    .add(Field.of("str", String.class))
                                    .add("return str == null ? null : " + enumShortName + "." + FROM_DATABASE_METHOD + "(str);")
                                )
                                .add(Method.of("toDatabaseType", String.class)
                                    .public_().add(OVERRIDE)
                                    .add(Field.of(enumVarName, enumType))
                                    .add("return " + enumVarName + " == null ? null : " + enumVarName + "." + TO_DATABASE_METHOD + "();")
                                )
                        ));

                        // Foreign key args
                        final Optional<FkHolder> fk =
                            EntityTranslatorSupport.getForeignKey(
                                translator.getSupport().tableOrThrow(), col
                            ).map(fkc -> new FkHolder(injector, fkc.getParentOrThrow()));

                        fk.ifPresent(holder -> {
                            params.add(ofReference(
                                holder.getForeignEmt().getSupport().entityName() + "." +
                                translator.getSupport().namer().javaStaticFieldName(
                                    holder.getForeignColumn().getJavaName()
                                ) + ","
                            ));
                        });

                        params.add(Value.ofReference(enumShortName + "::" + TO_DATABASE_METHOD));
                        params.add(Value.ofReference(enumShortName + "::" + FROM_DATABASE_METHOD));
                        params.add(Value.ofReference(enumShortName + ".class"));

                        field.set(ofInvocation(
                            fk.isPresent()
                                ? EnumForeignKeyField.class
                                : EnumField.class,
                            "create",
                            params.toArray(new Value<?>[params.size()])
                        ));
                    });
            });
        });
    }
}