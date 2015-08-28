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
package com.speedment.core.code.entity;

import com.speedment.api.Speedment;
import com.speedment.core.code.EntityAndManagerTranslator;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.PARAM;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.RETURN;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.THROWS;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import com.speedment.codegen.lang.models.values.ReferenceValue;
import static com.speedment.codegen.util.Formatting.shortName;
import com.speedment.api.config.Table;
import com.speedment.api.config.Dbms;
import com.speedment.core.code.entity.EntityTranslatorSupport.ReferenceFieldType;
import static com.speedment.util.JavaLanguage.javaStaticFieldName;
import static com.speedment.util.JavaLanguage.javaTypeName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import com.speedment.api.Entity;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends EntityAndManagerTranslator<Interface> {

    public EntityTranslator(Speedment speedment, Generator cg, Table configEntity) {
        super(speedment, cg, configEntity);
    }

    @Override
    protected Interface make(File file) {
        final Map<Table, List<String>> fkStreamers = new HashMap<>();

        final Interface iface = new InterfaceBuilder(ENTITY.getName())
            // Getters and Setters
            .addColumnConsumer((i, c) -> {
                final Type retType;
                if (c.isNullable()) {
                    retType = Type.of(Optional.class).add(
                        Generic.of().add(
                            Type.of(c.getMapping())
                        )
                    );
                } else {
                    retType = Type.of(c.getMapping());
                }
                i.add(
                    Method.of(GETTER_METHOD_PREFIX + typeName(c), retType)
                    .set(Javadoc.of(
                        "Returns the " + variableName(c) + " of this " + ENTITY.getName() + ". The " + variableName(c) + " field corresponds to the database column "
                        + c.getRelativeName(Dbms.class) + "."
                    ).add(RETURN.setText("the " + variableName(c) + " of this " + ENTITY.getName())))
                );

                i.add(Method.of(SETTER_METHOD_PREFIX + typeName(c), ENTITY.getType())
                    .add(Field.of(variableName(c), Type.of(c.getMapping())))
                    .set(Javadoc.of(
                        "Sets the " + variableName(c) + " of this " + ENTITY.getName() + ". The " + variableName(c) + " field corresponds to the database column "
                        + c.getRelativeName(Dbms.class) + "."
                    )
                        .add(PARAM.setValue(variableName(c)).setText("to set of this " + ENTITY.getName()))
                        .add(RETURN.setText("this " + ENTITY.getName() + " instance")))
                );

            })
            // Fields
            .addColumnConsumer((i, c) -> {
                final ReferenceFieldType ref = EntityTranslatorSupport.getReferenceFieldType(file, table(), c, ENTITY.getType());

                final Type entityType = ENTITY.getType();
                final String shortEntityName = ENTITY.getName();

                file.add(Import.of(entityType));

                final String getter, finder;
                if (c.isNullable()) {
                    getter = "o -> o.get" + typeName(c) + "().orElse(null)";
                    finder = EntityTranslatorSupport.getForeignKey(table(), c)
                        .map(fkc -> {
                            return ", fk -> fk.find"
                                + javaTypeName(c.getName())
                                + "().orElse(null)";
                        }).orElse("");
                } else {
                    getter = shortEntityName + "::get" + typeName(c);
                    finder = EntityTranslatorSupport.getForeignKey(table(), c)
                        .map(fkc -> {
                            return ", "
                                + shortEntityName + "::find"
                                + javaTypeName(c.getName());

                        }).orElse("");
                }
                final String setter = ", " + shortEntityName + "::set" + typeName(c);

                file.add(Import.of(ref.implType));
                i.add(Field.of(javaStaticFieldName(c.getName()), ref.type)
                    .public_().final_().static_()
                    .set(new ReferenceValue(
                        "new " + shortName(ref.implType.getName())
                        + "<>(\""
                        + c.getName()
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
            .addForeignKeyReferencesThisTableConsumer((i, fk) -> {
                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
//                final Type fieldClassType = Type.of(Formatting.packageName(fu.getEmt().ENTITY.getType().getName()).get() + "." + typeName(fu.getTable()) + "Field");
//                file.add(Import.of(fieldClassType));
                fu.imports().forEachOrdered(file::add);
                final String methodName = EntityTranslatorSupport.pluralis(fu.getTable()) + "By" + typeName(fu.getColumn());
                // Record for later use in the construction of aggregate streamers
                fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);
                final Type returnType = Type.of(Stream.class).add(fu.getEmt().GENERIC_OF_ENTITY);
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
                )
                    .add(RETURN.setText(
                        "a {@link Stream} of all "
                        + "{@link " + typeName(fu.getTable()) + "} Entities  that references this Entity by "
                        + "the foreign key field that can be obtained using {@link " + typeName(fu.getTable()) + "#get" + typeName(fu.getColumn()) + "()}")
                    )
                );

                i.add(method);
            })
            .addForeignKeyConsumer((i, fk) -> {

                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
                fu.imports().forEachOrdered(file::add);

                final Type returnType;
                if (fu.getColumn().isNullable()) {
                    file.add(Import.of(Type.of(Optional.class)));
                    returnType = Type.of(Optional.class).add(fu.getForeignEmt().GENERIC_OF_ENTITY);

                } else {
                    returnType = fu.getForeignEmt().ENTITY.getType();
                }

                final Method method = Method.of("find" + typeName(fu.getColumn()), returnType);

                final String returns = "the foreign key Entity {@link " + typeName(fu.getForeignTable()) + "} referenced "
                    + "by the field that can be obtained using {@link " + ENTITY.getName() + "#get" + typeName(fu.getColumn()) + "()}";
                method.set(Javadoc.of(
                    "Finds and returns " + returns + "."
                ).add(RETURN.setText(returns)
                ));

                i.add(method);
            })
            .build()
            .public_()
            .add(Type.of(Entity.class).add(Generic.of().add(ENTITY.getType())));

        // Create aggregate streaming functions, if any
        fkStreamers.keySet().stream().forEach((referencingTable) -> {
            final List<String> methodNames = fkStreamers.get(referencingTable);
            if (!methodNames.isEmpty()) {
                final Method method = Method.of(EntityTranslatorSupport.pluralis(referencingTable), Type.of(Stream.class).add(new GenericImpl(typeName(referencingTable))));
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
                )
                    .add(RETURN.setText(
                        "a <em>distinct</em> {@link Stream} of all {@link " + typeName(referencingTable) + "} "
                        + "Entities that references this Entity by a foreign key")
                    )
                );

                iface.add(method);
            }
        });

        // Requred for persist() et. al
//        file.add(Import.of(Type.of(Platform.class)));
//        file.add(Import.of(Type.of(ManagerComponent.class)));
//        file.add(Import.of(Type.of(Optional.class)));

        /*

        iface
            //.add(entityAnnotation(file))
            //.add(builder())
            //.add(toBuilder())
            .add(toJson())
            .add(EntityTranslatorSupport.toJsonExtended(ENTITY.getType()).default_().add("return jsonFormatter.apply(this);"))
            //.add(stream())
            .add(persist())
            .add(update())
            .add(remove())
            .add(persistWithListener())
            .add(updateWithListener())
            .add(removeWithListener());
//                .add(manager()); */
        return iface;
    }

//    private Optional<ForeignKeyColumn> getForeignKey(Column column) {
//        return table().streamOf(ForeignKey.class)
//            .filter(ForeignKey::isEnabled)
//            .flatMap(fk -> fk.streamOf(ForeignKeyColumn.class))
//            .filter(ForeignKeyColumn::isEnabled)
//            .filter(fkc -> fkc.getColumn().equals(column))
//            .findFirst();
//
//    }
//    private Method manager() {
//        return Method.of("manager", MANAGER.getType()).static_()
//                .add("return Platform.get().get(ManagerComponent.class).manager(" + MANAGER.getName() + ".class);");
//    }
    private Method persist() {
        return EntityTranslatorSupport.persist(ENTITY.getType())
            .set(persistJavadoc("")
            );

    }

    private Method update() {
        return EntityTranslatorSupport.update(ENTITY.getType())
            .set(updateJavadoc("")
            );
    }

    private Method remove() {
        return EntityTranslatorSupport.remove(ENTITY.getType())
            .set(removeJavadoc(""));
    }

    private Javadoc persistJavadoc(String extraInfo) {
        return Javadoc.of("Persists the provided entity to the underlying database and returns a "
            + "potentially updated entity.If the persistence fails for any reason, an "
            + "unchecked {@link SpeedmentException} is thrown. "
            + " <p> "
            + extraInfo
            + "It is unspecified if the returned updated entity is the same provided "
            + "entity instance or another entity instance. It is erroneous to assume "
            + "either, so you should use only the returned entity after the method has "
            + "been called. However, it is guaranteed that the provided entity is "
            + "untouched if an exception is thrown. "
            + " <p> "
            + "The fields of returned entity instance may differ from the provided "
            + "entity fields due to auto generated column(s) or because of any other "
            + "modification that the underlying database imposed on the persisted "
            + "entity."
        )
            .add(RETURN.setText("an entity reflecting the result of the persisted entity"))
            .add(THROWS.setText("SpeedmentException if the underlying database throws an exception (e.g. SQLException)"));

    }

    private Javadoc updateJavadoc(String extraInfo) {
        return Javadoc.of("Updates the provided entity in the underlying database and returns a "
            + "potentially updated entity.If the update fails for any reason, an "
            + "unchecked {@link SpeedmentException} is thrown. "
            + " <p> "
            + extraInfo
            + "It is unspecified if the returned updated entity is the same provided "
            + "entity instance or another entity instance. It is erroneous to assume "
            + "either, so you should use only the returned entity after the method has "
            + "been called. However, it is guaranteed that the provided entity is "
            + "untouched if an exception is thrown. "
            + " <p> "
            + "The fields of returned entity instance may differ from the provided "
            + "entity fields due to auto generated column(s) or because of any other "
            + "modification that the underlying database imposed on the persisted "
            + "entity."
            + " <p> "
            + "Entities are uniquely identified by their primary key(s)."
        )
            .add(RETURN.setText("an entity reflecting the result of the updated entity"))
            .add(THROWS.setText("SpeedmentException if the underlying database throws an exception (e.g. SQLException)"));

    }

    private Javadoc removeJavadoc(String extraInfo) {
        return Javadoc.of("Removes the provided entity from the underlying database and returns the "
            + "provided entity instance.  If the deletion fails for any reason, an "
            + "unchecked {@link SpeedmentException} is thrown. "
            + " <p> "
            + extraInfo
            + "Entities are uniquely identified by their primary key(s)."
        )
            .add(RETURN.setText("the provided entity instance"))
            .add(THROWS.setText("SpeedmentException if the underlying database throws an exception (e.g. SQLException)"));
    }

    private Method persistWithListener() {
        return EntityTranslatorSupport.persistWithListener(ENTITY.getType())
            .set(addConsumerResult(updateJavadoc(
                "The {@link MetaResult} {@link Consumer} provided will be called, with meta data regarding the underlying "
                + "database transaction, after the persistence was attempted."
                + " <p> "
            )
            ));
    }

    private Method updateWithListener() {
        return EntityTranslatorSupport.updateWithListener(ENTITY.getType())
            .set(addConsumerResult(updateJavadoc(
                "The {@link MetaResult} {@link Consumer} provided will be called, with meta data regarding the underlying "
                + "database transaction, after the update was attempted."
                + " <p> "
            )
            ));
    }

    private Method removeWithListener() {
        return EntityTranslatorSupport.removeWithListener(ENTITY.getType())
            .set(addConsumerResult(removeJavadoc(
                "The {@link MetaResult} {@link Consumer} provided will be called, with meta data regarding the underlying "
                + "database transaction, after the removal was attempted."
                + " <p> "
            ))
            );
    }

    private Javadoc addConsumerResult(Javadoc javadoc) {
        return javadoc.add(PARAM.setValue(EntityTranslatorSupport.CONSUMER_NAME).setText("the {@link MetaResult} {@link Consumer} to use"));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An interface";
    }

    @Override
    protected String getFileName() {
        return ENTITY.getName();

    }

}
