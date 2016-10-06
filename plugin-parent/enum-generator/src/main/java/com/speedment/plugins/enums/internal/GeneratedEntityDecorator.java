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
package com.speedment.plugins.enums.internal;

import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.EnumConstant;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.generator.translator.JavaClassTranslator;
import com.speedment.generator.translator.TranslatorDecorator;
import com.speedment.plugins.enums.StringToEnumTypeMapper;
import com.speedment.runtime.config.Table;

import java.lang.reflect.Type;
import java.util.List;

import static com.speedment.common.codegen.util.Formatting.indent;
import static com.speedment.common.codegen.util.Formatting.shortName;
import com.speedment.common.codegen.model.Value;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   1.0.0
 */
public final class GeneratedEntityDecorator implements TranslatorDecorator<Table, Interface> {
    
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
        translator.onMake(builder -> {
            builder.forEveryTable((intrf, table) -> {
                table.columns()
                    .filter(col -> col.getTypeMapper()
                        .filter(StringToEnumTypeMapper.class.getName()::equals)
                        .isPresent()
                    ).forEach(col -> {
                        final JavaLanguageNamer namer = translator.getSupport().namer();
                        
                        final String colEnumName = EnumGeneratorUtil.enumNameOf(col, injector);
                        final List<String> constants = EnumGeneratorUtil.enumConstantsOf(col);
                        final Type enumType = new GeneratedEnumType(colEnumName, constants);
                        
                        final Enum colEnum = Enum.of(shortName(colEnumName))
                            .add(Field.of(DATABASE_NAME_FIELD, String.class).private_().final_());
                        
                        /*
                         * Generate enum constants
                         */
                        
                        constants.forEach(constant -> {
                            final String javaName = namer.javaStaticFieldName(constant);
                            colEnum.add(EnumConstant.of(javaName).add(Value.ofText(constant)));
                        });
                        
                        /*
                         * Generate constructor
                         */
                        colEnum.add(Constructor.of()
                            .add(Field.of(DATABASE_NAME_FIELD, String.class))
                            .add("this." + DATABASE_NAME_FIELD + " = " + DATABASE_NAME_FIELD + ";")
                        );
                        
                        /*
                         * Generate fromDatabase()-method
                         */
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
                        
                        /*
                         * Generate toDatabase()-method
                         */
                        colEnum.add(Method.of(TO_DATABASE_METHOD, String.class)
                            .public_().add("return " + DATABASE_NAME_FIELD + ";")
                        );
                        
                        /*
                         * Add it to the interface.
                         */
                        intrf.add(colEnum);
                    });
            });
        });
    }
}