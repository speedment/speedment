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
package com.speedment.generator.internal.manager;

import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.TranslatorSupport;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import com.speedment.generator.internal.util.EntityTranslatorSupport;
import com.speedment.generator.internal.util.FkHolder;
import com.speedment.generator.util.JavaLanguageNamer;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.manager.sql.SqlManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.internal.model.constant.DefaultJavadocTag.PARAM;
import static com.speedment.common.codegen.internal.model.constant.DefaultJavadocTag.RETURN;
import com.speedment.common.codegen.internal.model.constant.DefaultType;
import static com.speedment.generator.internal.util.ColumnUtil.usesOptional;

/**
 *
 * @author Emil Forslund
 */
public final class GeneratedManagerTranslator extends EntityAndManagerTranslator<Interface> {

    private @Inject Injector injector;
    private @Inject JavaLanguageNamer namer;
    
    public GeneratedManagerTranslator(Table table) {
        super(table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        final Map<Table, List<String>> fkStreamers = new HashMap<>();
        
        return newBuilder(file, getSupport().generatedManagerName())
            .forEveryTable((intf, table) -> {
                intf.public_()
                    .add(Type.of(SqlManager.class).add(Generic.of().add(getSupport().entityType())))
                    .add(generatePrimaryKeyFor(file))
                    .add(Method.of("getManagerClass", Type.of(Class.class).add(Generic.of().add(getSupport().managerType()))).default_().add(OVERRIDE)
                        .add("return " + getSupport().managerName() + ".class;"))
                    .add(Method.of("getEntityClass", Type.of(Class.class).add(Generic.of().add(getSupport().entityType()))).default_().add(OVERRIDE)
                        .add("return " + getSupport().entityName() + ".class;"))
                    .add(generateGetPrimaryKeyClasses(file));
            })
           
            /*** Add streamers from back pointing FK:s ***/
            .forEveryForeignKeyReferencingThis((intrf, fk) -> {
                final FkHolder fu = new FkHolder(injector, fk);
                file.add(Import.of(fu.getEmt().getSupport().entityType()));

                Import imp = Import.of(fu.getEmt().getSupport().entityType());
                file.add(imp);

                final String methodName = EntityTranslatorSupport.FIND
                    + EntityTranslatorSupport.pluralis(fu.getTable(), getSupport().namer())
                    + "By" + getSupport().typeName(fu.getColumn());
                
                /*** Record for later use in the construction of aggregate streamers ***/
                fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);
                final Type returnType = DefaultType.stream(fu.getEmt().getSupport().entityType());
                final Method method = Method.of(methodName, returnType);
                
                method.add(Field.of("entity", fu.getForeignEmt().getSupport().entityType()));

                method.set(Javadoc.of(
                        "Creates and returns a {@link Stream} of all "
                        + "{@link " + getSupport().typeName(fu.getTable()) + "} Entities that references this Entity by "
                        + "the foreign key field that can be obtained using {@link " + getSupport().typeName(fu.getTable()) + "#get" + getSupport().typeName(fu.getColumn()) + "()}. "
                        + "The order of the Entities are undefined and may change from time to time. "
                        + "<p>\n"
                        + "Using this method, you may \"walk the graph\" and jump "
                        + "directly between referencing Entities without using {@code JOIN}s."
                        + "<p>\n"
                        + "N.B. The current implementation supports lazy-loading of the referencing Entities."
                    )
                    .add(PARAM.setValue("entity").setText("the entity to read the column value from"))
                    .add(RETURN.setText(
                        "a {@link Stream} of all "
                        + "{@link " + getSupport().typeName(fu.getTable()) + "} Entities  that references this Entity by "
                        + "the foreign key field that can be obtained using {@link " + getSupport().typeName(fu.getTable()) + "#get" + getSupport().typeName(fu.getColumn()) + "()}")
                    )
                );

                intrf.add(method);
            })
            
            /*** Add ordinary finders ***/
            .forEveryForeignKey((intrf, fk) -> {

                final FkHolder fu = new FkHolder(injector, fk);

                final Type returnType;
                if (usesOptional(fu.getColumn())) {
                    file.add(Import.of(Type.of(Optional.class)));
                    returnType = Type.of(Optional.class).add(Generic.of().add(fu.getForeignEmt().getSupport().entityType()));

                } else {
                    returnType = fu.getForeignEmt().getSupport().entityType();
                }

                final Method method = Method.of("find" + getSupport().typeName(fu.getColumn()), returnType)
                    .add(Field.of("entity", fu.getEmt().getSupport().entityType()));

                final String returns = 
                    "the foreign key Entity {@link " + 
                    getSupport().typeName(fu.getForeignTable()) + "} referenced " +
                    "by the field that can be obtained using {@link " + 
                    getSupport().entityName() + "#get" + 
                    getSupport().typeName(fu.getColumn()) + "()}";

                method.set(Javadoc.of(
                        "Finds and returns " + returns + ".\n<p>\n" +
                        "N.B. The current implementation only supports lazy-loading " +
                        "of the referenced Entities. This means that if you " +
                        "traverse N " + getSupport().entityName() + " entities and call this " +
                        "method for each one, there will be N SQL-queries executed."
                    )
                    .add(PARAM.setValue("entity").setText("entity to read column value from"))
                    .add(RETURN.setText(returns))
                );

                intrf.add(method);
            })
            
            .forEveryTable(Phase.POST_MAKE, (intrf, table) -> {
                /*** Create aggregate streaming functions, if any ***/
                fkStreamers.keySet().stream().forEach((referencingTable) -> {
                    final List<String> methodNames = fkStreamers.get(referencingTable);
                    final TranslatorSupport<Table> foreignSupport = new TranslatorSupport<>(injector, referencingTable);

                    if (!methodNames.isEmpty()) {
                        final Method method = Method.of(
                            EntityTranslatorSupport.FIND + 
                            EntityTranslatorSupport.pluralis(referencingTable, getSupport().namer()),
                            DefaultType.stream(foreignSupport.entityType())
                        );

                        method.add(Field.of("entity", getSupport().entityType()));

                        method.set(Javadoc.of(
                                "Creates and returns a <em>distinct</em> {@link Stream} of all " +
                                "{@link " + getSupport().typeName(referencingTable) + "} Entities that "+
                                "references this Entity by a foreign key. The order of the "+
                                "Entities are undefined and may change from time to time.\n"+
                                "<p>\n"+
                                "Note that the Stream is <em>distinct</em>, meaning that " +
                                "referencing Entities will only appear once in the Stream, even " +
                                "though they may reference this Entity by several columns.\n" +
                                "<p>\n" +
                                "Using this method, you may \"walk the graph\" and jump " +
                                "directly between referencing Entities without using {@code JOIN}s.\n" +
                                "<p>\n" +
                                "N.B. The current implementation supports lazy-loading of the referencing Entities."
                            )
                            .add(PARAM.setValue("entity").setText("where to read the column value from"))
                            .add(RETURN.setText(
                                "a <em>distinct</em> {@link Stream} of all {@link " + 
                                getSupport().typeName(referencingTable) + "} " +
                                "Entities that references this Entity by a foreign key"
                            ))
                        );

                        intrf.add(method);
                    }
                });
            })
            
            .build();
    }

    protected Method generatePrimaryKeyFor(File file) {
        final Method method = Method.of("primaryKeyFor", typeOfPK()).default_().add(OVERRIDE)
            .add(Field.of("entity", getSupport().entityType()));

        if (primaryKeyColumns().count() == 1) {
            method.add("return entity.get" + getSupport().typeName(primaryKeyColumns().findAny().get().findColumn().get()) + "();");
        } else {
            file.add(Import.of(Type.of(Arrays.class)));
            method.add(primaryKeyColumns()
                .map(pkc -> "entity.get" + getSupport().typeName(pkc.findColumn().get()) + "()")
                .collect(Collectors.joining(", ", "return Arrays.asList(", ");"))
            );
        }

        return method;
    }

    protected Method generateGetPrimaryKeyClasses(File file) {
        return Method.of("getPrimaryKeyClasses", pkTupleType()).add(OVERRIDE);
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base manager";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedManagerName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
}