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
package com.speedment.internal.core.code.entity;

import com.speedment.Speedment;
import com.speedment.internal.core.code.EntityAndManagerTranslator;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Interface;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.PARAM;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.RETURN;
import com.speedment.internal.codegen.lang.models.implementation.GenericImpl;
import com.speedment.internal.codegen.lang.models.values.ReferenceValue;
import static com.speedment.internal.codegen.util.Formatting.shortName;
import com.speedment.config.db.Table;
import com.speedment.config.db.Dbms;
import com.speedment.internal.core.code.entity.EntityTranslatorSupport.ReferenceFieldType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import com.speedment.Entity;
import static com.speedment.internal.util.document.DocumentUtil.relativeName;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends EntityAndManagerTranslator<Interface> {

    public EntityTranslator(Speedment speedment, Generator gen, Table doc) {
        super(speedment, gen, doc, Interface::of);
    }

    @Override
    protected Interface make(File file) {
        final Map<Table, List<String>> fkStreamers = new HashMap<>();

        final Interface iface = newBuilder(file, entity.getName())
                // Getters
                .forEveryColumn((i, c) -> {
                    final Type retType;
                    if (c.isNullable()) {
                        retType = Type.of(Optional.class).add(
                                Generic.of().add(
                                        Type.of(c.findTypeMapper().getJavaType())
                                )
                        );
                    } else {
                        retType = Type.of(c.findTypeMapper().getJavaType());
                    }
                    i.add(Method.of(GETTER_METHOD_PREFIX + typeName(c), retType)
                            .set(Javadoc.of("Returns the " + variableName(c) + " of this " + entity.getName() + ". The " + variableName(c) + " field corresponds to the database column "
                                    + relativeName(c, Dbms.class) + "."
                            //+ c.getRelativeName(Dbms.class) + "."
                            ).add(RETURN.setText("the " + variableName(c) + " of this " + entity.getName())))
                    );

                })
                // Setters
                .forEveryColumn((i, c) -> {
                    i.add(Method.of(SETTER_METHOD_PREFIX + typeName(c), entity.getType())
                            .add(Field.of(variableName(c), Type.of(c.findTypeMapper().getJavaType())))
                            .set(Javadoc.of("Sets the " + variableName(c) + " of this " + entity.getName() + ". The " + variableName(c) + " field corresponds to the database column "
                                    + relativeName(c, Dbms.class) + "."
                            )
                                    .add(PARAM.setValue(variableName(c)).setText("to set of this " + entity.getName()))
                                    .add(RETURN.setText("this " + entity.getName() + " instance")))
                    );

                })
                // Fields
                .forEveryColumn((i, c) -> {
                    final ReferenceFieldType ref = EntityTranslatorSupport.getReferenceFieldType(file, table(), c, entity.getType(), javaLanguageNamer());

                    final Type entityType = entity.getType();
                    final String shortEntityName = entity.getName();

                    file.add(Import.of(entityType));

                    final String getter, finder;
                    if (c.isNullable()) {
                        getter = "o -> o.get" + typeName(c) + "().orElse(null)";
                        finder = EntityTranslatorSupport.getForeignKey(table(), c)
                                .map(fkc -> {
                                    return ", fk -> fk.find"
                                            + javaLanguageNamer().javaTypeName(c.getJavaName())
                                            + "().orElse(null)";
                                }).orElse("");
                    } else {
                        getter = shortEntityName + "::get" + typeName(c);
                        finder = EntityTranslatorSupport.getForeignKey(table(), c)
                                .map(fkc -> {
                                    return ", "
                                            + shortEntityName + "::find"
                                            + javaLanguageNamer().javaTypeName(c.getJavaName());

                                }).orElse("");
                    }
                    final String setter = ", " + shortEntityName + "::set" + typeName(c);

                    file.add(Import.of(ref.implType));
                    i.add(Field.of(javaLanguageNamer().javaStaticFieldName(c.getJavaName()), ref.type)
                            .public_().final_().static_()
                            .set(new ReferenceValue(
                                    "new " + shortName(ref.implType.getName())
                                    + "<>(\""
                                    + c.getJavaName()
                                    + "\", "
                                    + getter
                                    + setter
                                    + finder
                                    + ")"
                            ))
                            .set(Javadoc.of(
                                    "This Field corresponds to the {@link " + shortEntityName + "} field that can be obtained using the "
                                    + "{@link " + shortEntityName + "#get" + typeName(c) + "()} method."
                            )));

                })
                // Add streamers from back pointing FK:s
                .forEveryForeignKeyReferencingThis((i, fk) -> {
                    final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
                    file.add(Import.of(fu.getEmt().entity().getType()));

                    Import imp = Import.of(fu.getEmt().entity().getType());
                    //System.out.println("imp=" + imp.getType().getName());
                    file.add(imp);

                    fu.imports().forEachOrdered(file::add);
                    final String methodName = EntityTranslatorSupport.FIND
                            + EntityTranslatorSupport.pluralis(fu.getTable(), javaLanguageNamer())
                            + "By" + typeName(fu.getColumn());
                    // Record for later use in the construction of aggregate streamers
                    fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);
                    final Type returnType = Type.of(Stream.class).add(fu.getEmt().genericOfEntity());
                    final Method method = Method.of(methodName, returnType);
//                    .default_()
//                    .add("return " + managerTypeName(fu.getTable()) + ".get()")
//                    //.add("        .stream().filter(" + variableName(fu.getTable()) + " -> Objects.equals(this." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "(), " + variableName(fu.getTable()) + "." + GETTER_METHOD_PREFIX + typeName(fu.getColumn()) + "()));");
//                    .add("        .stream().filter(" + typeName(fu.getTable()) + "Field." + JavaLanguage.javaStaticFieldName(fu.getColumn().getName()) + ".equal(this." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "()));");

                    method.set(Javadoc.of(
                            "Creates and returns a {@link Stream} of all "
                            + "{@link " + typeName(fu.getTable()) + "} Entities that references this Entity by "
                            + "the foreign key field that can be obtained using {@link " + typeName(fu.getTable()) + "#get" + typeName(fu.getColumn()) + "()}. "
                            + "The order of the Entities are undefined and may change from time to time. "
                            + "<p>\n"
                            + "Using this method, you may \"walk the graph\" and jump "
                            + "directly between referencing Entities without using {@code JOIN}s."
                            + "<p>\n"
                            + "N.B. The current implementation supports lazy-loading of the referencing Entities."
                    )
                            .add(RETURN.setText(
                                    "a {@link Stream} of all "
                                    + "{@link " + typeName(fu.getTable()) + "} Entities  that references this Entity by "
                                    + "the foreign key field that can be obtained using {@link " + typeName(fu.getTable()) + "#get" + typeName(fu.getColumn()) + "()}")
                            )
                    );

                    i.add(method);
                })
                .forEveryForeignKey((i, fk) -> {

                    final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
                    fu.imports().forEachOrdered(file::add);

                    final Type returnType;
                    if (fu.getColumn().isNullable()) {
                        file.add(Import.of(Type.of(Optional.class)));
                        returnType = Type.of(Optional.class).add(fu.getForeignEmt().genericOfEntity());

                    } else {
                        returnType = fu.getForeignEmt().entity().getType();
                    }

                    final Method method = Method.of("find" + typeName(fu.getColumn()), returnType);

                    final String returns = "the foreign key Entity {@link " + typeName(fu.getForeignTable()) + "} referenced "
                            + "by the field that can be obtained using {@link " + entity.getName() + "#get" + typeName(fu.getColumn()) + "()}";

                    method.set(Javadoc.of("Finds and returns " + returns + "."
                            + "<p>\n"
                            + "N.B. The current implementation only supports lazy-loading of the referenced Entities. This means that if you "
                            + "traverse N " + entity.getName() + " entities and call this method for each one, there will be N SQL-queries executed."
                    ).add(RETURN.setText(returns)
                    ));

                    i.add(method);
                })
                .build()
                .public_()
                .add(Type.of(Entity.class).add(Generic.of().add(entity.getType())));

        // Create aggregate streaming functions, if any
        fkStreamers.keySet().stream().forEach((referencingTable) -> {
            final List<String> methodNames = fkStreamers.get(referencingTable);
            if (!methodNames.isEmpty()) {
                final Method method = Method.of(EntityTranslatorSupport.FIND + EntityTranslatorSupport.pluralis(referencingTable,
                        javaLanguageNamer()), Type.of(Stream.class).add(new GenericImpl(typeName(referencingTable))));
//                    .default_();
//                if (methodNames.size() == 1) {
//                    method.add("return " + methodNames.get(0) + "();");
//                } else {
//                    file.add(Import.of(Type.of(Function.class)));
//                    method.add("return Stream.of("
//                        + methodNames.stream().map(n -> n + "()").collect(Collectors.joining(", "))
//                        + ").flatMap(Function.identity()).distinct();");
//                }
                method.set(Javadoc.of(
                        "Creates and returns a <em>distinct</em> {@link Stream} of all "
                        + "{@link " + typeName(referencingTable) + "} Entities that "
                        + "references this Entity by a foreign key. The order of the "
                        + "Entities are undefined and may change from time to time. "
                        + "<p>\n"
                        + "Note that the Stream is <em>distinct</em>, meaning that "
                        + "referencing Entities will only appear once in the Stream, even "
                        + "though they may reference this Entity by several columns. "
                        + "<p>\n"
                        + "Using this method, you may \"walk the graph\" and jump "
                        + "directly between referencing Entities without using {@code JOIN}s."
                        + "<p>\n"
                        + "N.B. The current implementation supports lazy-loading of the referencing Entities."
                )
                        .add(RETURN.setText(
                                "a <em>distinct</em> {@link Stream} of all {@link " + typeName(referencingTable) + "} "
                                + "Entities that references this Entity by a foreign key")
                        )
                );

                iface.add(method);
            }
        });

        return iface;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An interface";
    }

    @Override
    protected String getFileName() {
        return entity.getName();

    }

}
