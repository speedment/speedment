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
package com.speedment.plugins.reactor.internal.translator;

import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.common.codegen.internal.model.constant.DefaultType;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.VOID;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.WILDCARD;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import static com.speedment.common.codegen.internal.util.Formatting.shortName;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Generic;
import static com.speedment.common.codegen.model.Generic.BoundType.EXTENDS;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.generator.JavaClassTranslator;
import static com.speedment.generator.Translator.Phase.POST_MAKE;
import com.speedment.generator.TranslatorDecorator;
import com.speedment.generator.TranslatorSupport;
import com.speedment.plugins.reactor.MaterializedView;
import com.speedment.plugins.reactor.Reactor;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class GeneratedApplicationImplDecorator implements TranslatorDecorator<Project, Class> {
    
    /*
    private final List<Reactor<?, ?>> _reactors;
    private final AccountEventView accountEventView;
    private final BookEventView bookEventView;
    private final UserEventView userEventView;
    
    public GeneratedLibraryApplicationImpl() {
        _reactors = new CopyOnWriteArrayList<>();
        
        accountEventView = new AccountEventViewImpl();
        bookEventView = new BookEventViewImpl();
        userEventView = new UserEventViewImpl();
    }
    
    @Override
    public AbstractApplicationBuilder<?, ?> newApplicationBuilder() {
        return new LibraryApplicationBuilder();
    }

    @Override
    public <ENTITY> MaterializedView<ENTITY, ?> viewOf(Class<ENTITY> entityType) {
        final MaterializedView<ENTITY, ?> view;
        
        if (entityType == AccountEvent.class) {
            view = (MaterializedView<ENTITY, ?>) accountEventView;
        } else if (entityType == BookEventView.class) {
            view = (MaterializedView<ENTITY, ?>) bookEventView;
        } else if (entityType == UserEventView.class) {
            view = (MaterializedView<ENTITY, ?>) userEventView;
        } else throw new UnsupportedOperationException(
            "Unknown entiy type '" + entityType.getSimpleName() + "'."
        );
        
        return view;
    }
    
    @Override
    public void start() {
        newReactor(AccountEvent.class, AccountEvent.ID, accountEventView);
        newReactor(BookEvent.class, BookEvent.ID, bookEventView);
        newReactor(UserEvent.class, UserEvent.ID, userEventView);
    }
    
    @Override
    public void stop() {
        super.stop();
        _reactors.forEach(Reactor::stop);
    }
    
    private <E, T extends Comparable<T>> void newReactor(Class<E> entityType, ComparableField<E, ?, T> field, MaterializedView<E, T> view) {
        final Consumer<List<E>> con = view;
        _reactors.add(Reactor.builder(managerOf(entityType), field)
            .withListener(con)
            .build());
    }
    */

    @Override
    public void apply(JavaClassTranslator<Project, Class> translator) {
        final Speedment speedment = translator.getSupport().speedment();
        
        translator.onMake((file, builder) -> {
            System.out.println("Decorating...");
            
            // This should be done once:
            builder.forEveryProject(POST_MAKE, (clazz, project) -> {
                System.out.println("...Decorating project " + project.getName() + "...");
                
                // Generate new field '_reactors'
                clazz.add(Field.of("_reactors", 
                        DefaultType.list(Type.of(Reactor.class)
                            .add(Generic.of().add(WILDCARD))
                            .add(Generic.of().add(WILDCARD))
                        )
                    ).private_().final_()
                );
                
                // Set '_reactors' in constructor.
                file.add(Import.of(Type.of(LinkedList.class)));
                clazz.getConstructors().forEach(constr -> {
                    constr.add("this._reactors = new LinkedList<>();");
                });
                
                // Generate views for each table
                final List<String> ifs = new LinkedList<>();
                project.dbmses()
                    .filter(Dbms::isEnabled)
                    .flatMap(Dbms::schemas)
                    .filter(Schema::isEnabled)
                    .flatMap(Schema::tables)
                    .filter(Table::isEnabled)
                    .forEachOrdered(table -> {
                        final TranslatorSupport support = new TranslatorSupport(speedment, table);
                        final String viewName     = support.variableName() + "View";
                        final String viewTypeName = support.typeName() + "View";
                        final Type viewType       = Type.of(support.basePackageName() + "." + viewTypeName);
                        
                        // Import required classes.
                        file.add(Import.of(Type.of(support.basePackageName() + "." + viewTypeName + "Impl")));
                        file.add(Import.of(support.entityImplType()));
                        
                        // Add view field.
                        clazz.add(Field.of(viewName, viewType).private_().final_());
                        
                        // Assign it in the constructor.
                        clazz.getConstructors().forEach(constr -> {
                            constr.add("this." + viewName + " = new " + viewTypeName + "Impl<>();");
                        });
                        
                        // Add if statement for the 'viewOf()'-method
                        ifs.add(new StringBuilder()
                            .append("if (entityType == ")
                            .append(support.typeName())
                            .append(".class) {\n")
                            .append(indent("view = (MaterializedView<ENTITY, ?>) " + viewName + ";"))
                            .append("}")
                            .toString()
                        );
                        
                        // Find the name of the primary key field.
                        final String pkName = table.primaryKeyColumns()
                            .map(PrimaryKeyColumn::findColumn)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(Column::getJavaName)
                            .map(translator.getNamer()::javaStaticFieldName)
                            .findFirst()
                            .orElseThrow(() -> new SpeedmentException(
                                "Error generating code. Table '" + table.getName() + 
                                "' does not appear to have a valid primary key."
                            ));
                        
                        // Initialise reactor in start method
                        getOrCreate(clazz, "start")
                            .add("newReactor(" + support.typeName() + ".class, " + support.typeName() + "." + pkName + ", " + viewName + ");");
                    });
                
                // Generate the 'viewOf' method
                final Generic generic = Generic.of().setLowerBound("ENTITY");
                getOrCreate(clazz, "viewOf", Type.of(MaterializedView.class)
                    .add(generic)
                    .add(Generic.of().add(WILDCARD))
                ).add(generic)
                    .add(Field.of(
                        "entityType", 
                        Type.of(java.lang.Class.class).add(generic)
                    ))
                    .add(
                        "final MaterializedView<ENTITY, ?> view;",
                        "",
                        ifs.stream().collect(joinIfNotEmpty(" else ", "", " else ")) +
                        "throw new UnsupportedOperationException(\n" +
                        indent("\"Unknown entiy type '\" + entityType.getSimpleName() + \"'.\"") +
                        "\n);",
                        "",
                        "return view;"
                    );
                
                // Generate the 'start' method
                getOrCreate(clazz, "start").add("_reactors.forEach(Reactor::stop);");
                
                /*
                newReactor(AccountEvent.class, AccountEvent.ID, accountEventView);
                newReactor(BookEvent.class, BookEvent.ID, bookEventView);
                newReactor(UserEvent.class, UserEvent.ID, userEventView);
                */

                // Generate the 'stop' method
                getOrCreate(clazz, "stop").add("_reactors.forEach(Reactor::stop);");
                
                // Generate the 'newReactor' method
                clazz.add(Method.of("newReactor", VOID)
                    .private_()
                    .add(Generic.of().setLowerBound("E"))
                    .add(Generic.of().setLowerBound("T")
                        .setBoundType(EXTENDS)
                        .add(Type.of(Comparable.class)
                            .add(Generic.of().setLowerBound("T"))
                        )
                    )
                    .add(Field.of("entityType", Type.of(java.lang.Class.class)
                        .add(Generic.of().setLowerBound("E"))
                    ))
                    .add(Field.of("field", Type.of(ComparableField.class)
                        .add(Generic.of().setLowerBound("E"))
                        .add(Generic.of().add(WILDCARD))
                        .add(Generic.of().setLowerBound("T"))
                    ))
                    .add(Field.of("view", Type.of(MaterializedView.class)
                        .add(Generic.of().setLowerBound("E"))
                        .add(Generic.of().setLowerBound("T"))
                    ))
                    .call(() -> file.add(Import.of(Type.of(Consumer.class))))
                    .add(
                        "final Consumer<List<E>> con = view;",
                        "_reactors.add(Reactor.builder(speedment.managerOf(entityType), field)",
                        "    .withListener(con)",
                        "    .build());"
                    )
                );
                
                // This should be done for every view:
//                DocumentDbUtil.traverseOver(project, Table.class).forEach(table -> {
//                    final TranslatorSupport<Table> tableSupport = 
//                        new TranslatorSupport<>(translator.getSupport().speedment(), table);
//                    
//                    System.out.println("...Decorating table " + table.getName() + "...");
//                
//                    final String tableTypeName = tableSupport.typeName(table);
//
//                    // Generate fields for every materialized view.
//                    final String viewName = tableSupport.variableName() + "View";
//                    final Type viewType = Type.of(tableSupport.basePackageName() + "." + 
//                        tableTypeName + "View");
//
//                    clazz.add(Field.of(viewName, viewType)
//                        .protected_().final_()
//                    );
//
//                    // Set the views in the constructor
//                    clazz.getConstructors().forEach(constr -> {
//                        constr.add("this." + viewName + " = new " + shortName(viewType.getName()) + "Impl();");
//                    });
//                    
//                    file.add(Import.of(Type.of(tableSupport.basePackageName() + "." + 
//                        tableTypeName + "ViewImpl")));
//
//                    // Find the name of the primary key field.
//                    final String pkName = table.primaryKeyColumns()
//                        .map(PrimaryKeyColumn::findColumn)
//                        .filter(Optional::isPresent)
//                        .map(Optional::get)
//                        .map(Column::getJavaName)
//                        .map(translator.getNamer()::javaStaticFieldName)
//                        .findFirst()
//                        .orElseThrow(() -> new SpeedmentException(
//                            "Error generating code. Table '" + table.getName() + 
//                            "' does not appear to have a valid primary key."
//                        ));
//
//                    // Call 'newReactor' in the 'onLoad'-method for every view
//                    getOrCreate(clazz, "onLoad")
//                        .add("newReactor(" + tableTypeName + ".class, " +
//                            tableTypeName + "." + pkName + ", " + viewName + ");"
//                        );
//                    
//                    file.add(Import.of(Type.of(tableSupport.basePackageName() + "." + 
//                        tableTypeName)));
//                });
            });
        });
    }

    private static Method getOrCreate(Class clazz, String methodName) {
        return getOrCreate(clazz, methodName, VOID);
    }
    
    private static Method getOrCreate(Class clazz, String methodName, Type returnType) {
        return clazz.getMethods().stream()
            .filter(m -> m.getName().equals(methodName))
            .findAny().orElseGet(() -> {
                final Method method = Method.of(methodName, returnType)
                    .public_()
                    .add(OVERRIDE)
                    .add("super." + methodName + "();");

                clazz.add(method);

                return method;
            });
    }
}
