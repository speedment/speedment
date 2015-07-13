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

import com.speedment.codegen.util.Formatting;
import com.speedment.core.code.model.java.BaseEntityAndManagerTranslator;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.AnnotationUsage;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultType.LIST;
import static com.speedment.codegen.lang.models.constants.DefaultType.STRING;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import com.speedment.codegen.lang.models.values.ReferenceValue;
import static com.speedment.codegen.util.Formatting.indent;
import com.speedment.core.code.model.java.manager.EntityManagerTranslator;
import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.ForeignKey;
import com.speedment.core.config.model.ForeignKeyColumn;
import com.speedment.core.config.model.Table;
import com.speedment.core.core.entity.Entity;
import com.speedment.core.manager.metaresult.MetaResult;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.util.Pluralis;
import com.speedment.util.java.JavaLanguage;
import com.speedment.util.json.Json;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends BaseEntityAndManagerTranslator<Interface> {

    public EntityTranslator(Generator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Interface make(File file) {
        final Map<Table, List<String>> fkStreamers = new HashMap<>();

        final Interface iface = new InterfaceBuilder(ENTITY.getName())
            // Getters
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
                i.add(Method.of(GETTER_METHOD_PREFIX + typeName(c), retType));
            })
            // Add streamers from back pointing FK:s
            .addForeignKeyReferencesThisTableConsumer((i, fk) -> {
                final FkUtil fu = new FkUtil(fk);
                final Type fieldClassType = Type.of(Formatting.packageName(fu.getEmt().ENTITY.getType().getName()).get() + "." + typeName(fu.getTable()) + "Field");
                file.add(Import.of(fieldClassType));
                fu.imports().forEachOrdered(file::add);
                final String methodName = pluralis(fu.getTable()) + "By" + typeName(fu.getColumn());
                // Record for later use in the construction of aggregate streamers
                fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);
                final Type returnType = Type.of(Stream.class).add(fu.getEmt().GENERIC_OF_ENTITY);
                final Method method = Method.of(methodName, returnType)
                .default_()
                .add("return " + managerTypeName(fu.getTable()) + ".get()")
                //.add("        .stream().filter(" + variableName(fu.getTable()) + " -> Objects.equals(this." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "(), " + variableName(fu.getTable()) + "." + GETTER_METHOD_PREFIX + typeName(fu.getColumn()) + "()));");
                .add("        .stream().filter(" + typeName(fu.getTable()) + "Field." + JavaLanguage.javaStaticFieldName(fu.getColumn().getName()) + ".equal(this." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "()));");
                i.add(method);
            })
            .addForeignKeyConsumer((i, fk) -> {

//                default Optional<Hare> findRival() {
//                    return getRival()
//                        .flatMap(hare -> HareManager.get().stream()
//                            .filter(HareField.ID.equal(hare))
//                            .findAny()
//                        );
//                }
//                default Hare findOwner() {
//                    return HareManager.get().stream()
//                        .filter(HareField.ID.equal(getOwner()))
//                        .findAny().orElseThrow(() -> new SpeedmentException(
//                            "Foreign key constraint error. Owner is set to " + getOwner()
//                        ));
//                }
                final FkUtil fu = new FkUtil(fk);
                final Type fieldClassType = Type.of(Formatting.packageName(fu.getForeignEmt().ENTITY.getType().getName()).get() + "." + typeName(fu.getForeignTable()) + "Field");
                file.add(Import.of(fieldClassType));
                fu.imports().forEachOrdered(file::add);

                final Type returnType;
                if (fu.getColumn().isNullable()) {
                    file.add(Import.of(Type.of(Optional.class)));
                    returnType = Type.of(Optional.class).add(fu.getForeignEmt().GENERIC_OF_ENTITY);

                } else {
                    returnType = fu.getForeignEmt().ENTITY.getType();
                }

                final Method method = Method.of("find" + typeName(fu.getColumn()), returnType).default_();
                if (fu.getColumn().isNullable()) {
                    final String varName = variableName(fu.getForeignTable());
                    method.add("return get" + typeName(fu.getColumn()) + "()")
                    .add(indent(
                            ".flatMap(" + varName + " -> " + fu.getForeignEmt().MANAGER.getName() + ".get().stream()\n" + indent(
                                ".filter(" + typeName(fu.getForeignTable()) + "Field." + JavaLanguage.javaStaticFieldName(fu.getForeignColumn().getName()) + ".equal(" + varName + "))\n"
                                + ".findAny()"
                            ) + "\n);"
                        ));
                } else {
                    file.add(Import.of(Type.of(SpeedmentException.class)));
                    method.add("return " + fu.getForeignEmt().MANAGER.getName() + ".get().stream()\n" + indent(
                            ".filter(" + typeName(fu.getForeignTable()) + "Field." + JavaLanguage.javaStaticFieldName(fu.getForeignColumn().getName()) + ".equal(get" + typeName(fu.getColumn()) + "()))\n"
                            + ".findAny().orElseThrow(() -> new SpeedmentException(\n" + indent(
                                "\"Foreign key constraint error. " + typeName(fu.getForeignTable()) + " is set to \" + get" + typeName(fu.getColumn()) + "()\n"
                            ) + "));\n"
                        ));
                }

//                method.add("return " + fu.getForeignEmt().MANAGER.getName() + ".get()");
//                //method.add("        .stream().filter(" + variableName(fu.getForeignTable()) + " -> Objects.equals(this." + GETTER_METHOD_PREFIX + typeName(fu.getColumn()) + "(), " + variableName(fu.getForeignTable()) + "." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "())).findAny()" + getCode + ";");
//                method.add("        .stream().filter(" + typeName(fu.getForeignTable()) + "Field." + JavaLanguage.javaStaticFieldName(fu.getForeignColumn().getName()) + ".equal(this." + GETTER_METHOD_PREFIX + typeName(fu.getColumn()) + "())).findAny()" + getCode + ";");
                i.add(method);
            })
            .build()
            .public_();

        // Create aggregate streaming functions, if any
        fkStreamers.keySet().stream().forEach((referencingTable) -> {
            final List<String> methodNames = fkStreamers.get(referencingTable);
            if (!methodNames.isEmpty()) {
                final Method method = Method.of(pluralis(referencingTable), Type.of(Stream.class).add(new GenericImpl(typeName(referencingTable))))
                    .default_();
                if (methodNames.size() == 1) {
                    method.add("return " + methodNames.get(0) + "();");
                } else {
                    file.add(Import.of(Type.of(Function.class)));
                    method.add("return Stream.of("
                        + methodNames.stream().map(n -> n + "()").collect(Collectors.joining(", "))
                        + ").flatMap(Function.identity()).distinct();");
                }
                iface.add(method);
            }
        });

        // Requred for persist() et. al
//        file.add(Import.of(Type.of(Platform.class)));
//        file.add(Import.of(Type.of(ManagerComponent.class)));
        file.add(Import.of(Type.of(Optional.class)));

        iface
            .add(entityAnnotation(file))
            .add(builder())
            .add(toBuilder())
            .add(toJson())
            .add(toJsonExtended())
            .add(stream())
            .add(persist())
            .add(update())
            .add(remove())
            .add(persistWithListener())
            .add(updateWithListener())
            .add(removeWithListener());
//                .add(manager());

        return iface;
    }

    public String pluralis(Table table) {
        return Pluralis.INSTANCE.pluralizeJavaIdentifier(variableName(table));
    }

//    private Method manager() {
//        return Method.of("manager", MANAGER.getType()).static_()
//                .add("return Platform.get().get(ManagerComponent.class).manager(" + MANAGER.getName() + ".class);");
//    }
    private AnnotationUsage entityAnnotation(File file) {
        final AnnotationUsage result = AnnotationUsage.of(Type.of(Entity.class));
        result.put("managerType", new ReferenceValue(MANAGER.getName() + ".class"));
        result.put("builderType", new ReferenceValue(BUILDER.getName() + ".class"));
        if (primaryKeyColumns().count() > 1) {
            result.put("primaryKeyType", new ReferenceValue("List.class"));
            file.add(Import.of(LIST));
        } else {
            primaryKeyColumns().map(pkc -> {
                final Class<?> mapping = pkc.getColumn().getMapping();
                file.add(Import.of(Type.of(mapping)));
                return new ReferenceValue(mapping.getSimpleName() + ".class");
            }).forEach(pk -> result.put("primaryKeyType", pk));
        }
        return result;
    }

    private Method builder() {
        return Method.of("builder", BUILDER.getType()).static_()
            .add("return " + MANAGER.getName() + ".get().builder();");
    }

    private Method toBuilder() {
        return Method.of("toBuilder", BUILDER.getType()).default_()
            .add("return " + MANAGER.getName() + ".get().toBuilder(this);");
    }

    private Method toJson() {
        return Method.of("toJson", STRING).default_()
            .add("return " + MANAGER.getName() + ".get().toJson(this);");
    }

    private Method toJsonExtended() {
        return Method.of("toJson", STRING).default_()
            .add(Field.of("json", Type.of(Json.class)
                    .add(Generic.of().add(ENTITY.getType()))))
            .add("return json.build(this);");
    }

    private Method stream() {
        return Method.of("stream", Type.of(Stream.class
        ).add(new GenericImpl(ENTITY.getName()))).static_()
            .add("return " + MANAGER.getName() + ".get().stream();");
    }

    private Method persist() {
        return dbMethod("persist");
    }

    private Method update() {
        return dbMethod("update");
    }

    private Method remove() {
        return dbMethod("remove");
    }

    private Method persistWithListener() {
        return dbMethodWithListener("persist");
    }

    private Method updateWithListener() {
        return dbMethodWithListener("update");
    }

    private Method removeWithListener() {
        return dbMethodWithListener("remove");
    }

    private Method dbMethod(String name) {
        return Method.of(name, ENTITY.getOptionalType()).default_()
            .add("return " + MANAGER.getName() + ".get()." + name + "(this);");
    }

    private Method dbMethodWithListener(String name) {
        return Method.of(name, ENTITY.getOptionalType()).default_()
            .add(Field.of("listener", Type.of(Consumer.class).add(Generic.of().add(Type.of(MetaResult.class).add(Generic.of().add(ENTITY.getType()))))))
            .add("return " + MANAGER.getName() + ".get()." + name + "(this, listener);");
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An interface";
    }

    @Override
    protected String getFileName() {
        return ENTITY.getName();

    }

    private class FkUtil {

        private final ForeignKey fk;
        private final ForeignKeyColumn fkc;
        private final Column column;
        private final Table table;
        private final Column foreignColumn;
        private final Table foreignTable;
        private final EntityManagerTranslator emt;
        private final EntityManagerTranslator foreignEmt;

        public FkUtil(ForeignKey fk) {
            this.fk = fk;
            fkc = fk.stream()
                .filter(ForeignKeyColumn::isEnabled)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("FK " + fk.getName() + " does not have an enabled ForeignKeyColumn"));
            column = fkc.getColumn();
            table = column.ancestor(Table.class).get();
            foreignColumn = fkc.getForeignColumn();
            foreignTable = fkc.getForeignTable();
            emt = new EntityManagerTranslator(getCodeGenerator(), getTable());
            foreignEmt = new EntityManagerTranslator(getCodeGenerator(), getForeignTable());
        }

        public Stream<Import> imports() {
            final Stream.Builder<Type> sb = Stream.builder();
//            sb.add(Type.of(Platform.class));
//            sb.add(Type.of(ManagerComponent.class));
            sb.add(Type.of(Objects.class));
            sb.add(getEmt().ENTITY.getType());
            sb.add(getEmt().MANAGER.getType());
            sb.add(getForeignEmt().ENTITY.getType());
            sb.add(getForeignEmt().MANAGER.getType());
            return sb.build().map(t -> Import.of(t));
        }

        public Column getColumn() {
            return column;
        }

        public Table getTable() {
            return table;
        }

        public Column getForeignColumn() {
            return foreignColumn;
        }

        public Table getForeignTable() {
            return foreignTable;
        }

        public EntityManagerTranslator getEmt() {
            return emt;
        }

        public EntityManagerTranslator getForeignEmt() {
            return foreignEmt;
        }

    }

}
