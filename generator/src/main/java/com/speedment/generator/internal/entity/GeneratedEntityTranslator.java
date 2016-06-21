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
package com.speedment.generator.internal.entity;

import com.speedment.generator.internal.util.EntityTranslatorSupport;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.EnumConstant;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.internal.model.constant.DefaultJavadocTag.PARAM;
import static com.speedment.common.codegen.internal.model.constant.DefaultJavadocTag.RETURN;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.OPTIONAL;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.STRING;
import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import com.speedment.common.codegen.internal.model.value.TextValue;
import static com.speedment.common.codegen.internal.util.Formatting.shortName;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.TranslatorSupport;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import com.speedment.generator.internal.util.FkHolder;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.entity.Entity;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityTranslator extends EntityAndManagerTranslator<Interface> {

    public final static String IDENTIFIER_NAME = "Identifier";
    private @Inject Injector injector;
    
    public GeneratedEntityTranslator(Table table) {
        super(table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        
        final Enum identifier = Enum.of(IDENTIFIER_NAME)
            .add(Field.of("columnName", STRING).private_().final_())
            .add(Type.of(FieldIdentifier.class).add(Generic.of().add(getSupport().entityType())))
            .add(Constructor.of()
                .add(Field.of("columnName", STRING))
                .add("this.columnName = columnName;")
            )
            .add(Method.of("dbmsName", STRING).public_()
                .add(OVERRIDE)
                .add("return \"" + getSupport().dbmsOrThrow().getName() + "\";")
            )
            .add(Method.of("schemaName", STRING).public_()
                .add(OVERRIDE)
                .add("return \"" + getSupport().schemaOrThrow().getName() + "\";")
            )
            .add(Method.of("tableName", STRING).public_()
                .add(OVERRIDE)
                .add("return \"" + getSupport().tableOrThrow().getName() + "\";")
            )
            .add(Method.of("columnName", STRING).public_()
                .add(OVERRIDE)
                .add("return this.columnName;")
            );

        final Interface iface = newBuilder(file, getSupport().generatedEntityName())
            /*** General ***/
            .forEveryTable((intrf, col) -> {
                intrf.public_()
                    .add(identifier)
                    .add(Type.of(Entity.class).add(Generic.of().add(getSupport().entityType())));
            })
            
            /*** Getters ***/
            .forEveryColumn((intrf, col) -> {
                final Type retType = col.isNullable()
                    ? OPTIONAL.add(
                        Generic.of().add(
                            Type.of(col.findTypeMapper().getJavaType())
                        )
                    )
                    : Type.of(col.findTypeMapper().getJavaType());
                
                intrf.add(Method.of(GETTER_METHOD_PREFIX + getSupport().typeName(col), retType)
                    .set(Javadoc.of(
                            "Returns the " + getSupport().variableName(col) + 
                            " of this " + getSupport().entityName() + 
                            ". The " + getSupport().variableName(col) + 
                            " field corresponds to the database column " +
                            relativeName(col, Dbms.class, DATABASE_NAME) + "."
                        ).add(RETURN.setText(
                            "the " + getSupport().variableName(col) + 
                            " of this " + getSupport().entityName()
                        ))
                    )
                );
            })
            
            /*** Setters ***/
            .forEveryColumn((intrf, col) -> {
                intrf.add(Method.of(SETTER_METHOD_PREFIX + getSupport().typeName(col), getSupport().entityType())
                    .add(Field.of(getSupport().variableName(col), Type.of(col.findTypeMapper().getJavaType())))
                    .set(Javadoc.of(
                        "Sets the " + getSupport().variableName(col) + 
                        " of this " + getSupport().entityName() + 
                        ". The " + getSupport().variableName(col) + 
                        " field corresponds to the database column " +
                        relativeName(col, Dbms.class, DATABASE_NAME) + "."
                    )
                    .add(PARAM.setValue(getSupport().variableName(col)).setText("to set of this " + getSupport().entityName()))
                    .add(RETURN.setText("this " + getSupport().entityName() + " instance")))
                );
            })
            
            /*** Fields ***/
            .forEveryColumn((intrf, col) -> {
                
                final EntityTranslatorSupport.ReferenceFieldType ref = 
                    EntityTranslatorSupport.getReferenceFieldType(
                        file, getSupport().tableOrThrow(), col, getSupport().entityType(), getNamer()
                    );

                final String typeMapper      = col.getTypeMapper();
                final Type entityType        = getSupport().entityType();
                final String shortEntityName = getSupport().entityName();
                final Type typeMapperType    = Type.of(typeMapper);
                final String shortEntityVarName = getSupport().namer().javaVariableName(shortEntityName);

                file.add(Import.of(entityType));
                file.add(Import.of(typeMapperType));

                final String getter = col.isNullable() 
                    ? ("o -> o.get" + getSupport().typeName(col) + "().orElse(null)") 
                    : (shortEntityName + "::get" + getSupport().typeName(col));

                final String finder = EntityTranslatorSupport.getForeignKey(getSupport().tableOrThrow(), col)
                    .map(fkc -> {
                        final FkHolder fu = new FkHolder(injector, fkc.getParentOrThrow());
                        final TranslatorSupport<Table> fuSupport = fu.getEmt().getSupport();

                        file.add(Import.of(fuSupport.entityType()));

                        return ", (" + shortEntityVarName + ", fkManager) -> fkManager.findAny(" +
                            fu.getForeignEmt().getSupport().entityName() + "." +
                            fuSupport.namer().javaStaticFieldName(fu.getForeignColumn().getJavaName()
                            ) + ", " + shortEntityVarName + ".get" +
                                getNamer().javaTypeName(col.getJavaName()) + "()" + 

                                (col.isNullable() ? ".orElse(null)" : "")

                            + ").orElse(null)";
                    }).orElse("");

                final String setter = ", " + shortEntityName + "::set" + getSupport().typeName(col);

                final String constant = getNamer().javaStaticFieldName(col.getJavaName());
                identifier.add(EnumConstant.of(constant).add(new TextValue(col.getName())));

                file.add(Import.of(ref.implType));
                intrf.add(Field.of(getNamer().javaStaticFieldName(col.getJavaName()), ref.type)
                        .final_()
                        .set(new ReferenceValue(
                            "new " + shortName(ref.implType.getName())
                            + "<>(Identifier."
                            + constant
                            + ", "
                            + getter
                            + setter
                            + finder
                            + ", new "
                            + shortName(typeMapper)
                            + "(), " 
                            + DocumentDbUtil.isUnique(col)
                            + ")"
                        ))
                        .set(Javadoc.of(
                                "This Field corresponds to the {@link " + shortEntityName + "} field that can be obtained using the "
                                + "{@link " + shortEntityName + "#get" + getSupport().typeName(col) + "()} method."
                        )));
            })
            
            .build();
        
        return iface;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base interface";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedEntityName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
}