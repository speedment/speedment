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

import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.plugins.reactor.MaterializedView;
import com.speedment.plugins.reactor.Reactor;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.exception.SpeedmentException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultType.WILDCARD;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import static com.speedment.common.codegen.model.Generic.BoundType.EXTENDS;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.translator.JavaClassTranslator;
import static com.speedment.generator.translator.Translator.Phase.POST_MAKE;
import com.speedment.generator.translator.TranslatorDecorator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.field.trait.HasComparableOperators;
import java.lang.reflect.Type;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class GeneratedApplicationImplDecorator implements TranslatorDecorator<Project, Class> {
    
    private @Inject Injector injector;
    
    @Override
    public void apply(JavaClassTranslator<Project, Class> translator) {
        translator.onMake((file, builder) -> {
            
            // This should be done once:
            builder.forEveryProject(POST_MAKE, (clazz, project) -> {
                
                // Generate new field '_reactors'
                clazz.add(Field.of("_reactors", 
                        DefaultType.list(SimpleParameterizedType.create(
                            Reactor.class,
                            WILDCARD,
                            WILDCARD
                        ))
                    ).private_().final_()
                );
                
                // Set '_reactors' in constructor.
                file.add(Import.of(LinkedList.class));
                getOrCreate(clazz).forEach(constr -> {
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
                        final TranslatorSupport<Table> support = new TranslatorSupport<>(injector, table);
                        final String viewName     = support.variableName() + "View";
                        final String viewTypeName = support.typeName() + "View";
                        final Type viewType       = SimpleType.create(support.basePackageName() + "." + viewTypeName);
                        
                        // Import required classes.
                        file.add(Import.of(SimpleType.create(support.basePackageName() + "." + viewTypeName + "Impl")));
                        file.add(Import.of(support.entityType()));
                        
                        // Add view field.
                        clazz.add(Field.of(viewName, viewType).private_().final_());
                        
                        // Assign it in the constructor.
                        getOrCreate(clazz).forEach(constr -> {
                            constr.add("this." + viewName + " = new " + viewTypeName + "Impl(this);");
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
                            .map(translator.getSupport().namer()::javaStaticFieldName)
                            .findFirst()
                            .orElseThrow(() -> new SpeedmentException(
                                "Error generating code. Table '" + table.getName() + 
                                "' does not appear to have a valid primary key."
                            ));
                        
                        getOrCreate(clazz, "start")
                            .add("newReactor(" + support.typeName() + ".class, " + support.typeName() + "." + pkName + ", " + viewName + ");");
                    });
                
                // Generate the 'viewOf' method
                clazz.add(Method.of("viewOf", SimpleParameterizedType.create(
                        MaterializedView.class,
                        SimpleType.create("ENTITY"),
                        WILDCARD
                    ))
                    .add(Generic.of().setLowerBound("ENTITY"))
                    .public_()
                    .add(OVERRIDE)
                    .add(Field.of(
                        "entityType", 
                        DefaultType.classOf(SimpleType.create("ENTITY"))
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
                    )
                );

                // Generate the 'stop' method
                getOrCreate(clazz, "stop").add("_reactors.forEach(Reactor::stop);");
                
                // Generate the 'newReactor' method
                clazz.add(Method.of("newReactor", void.class)
                    .private_()
                    .add(Generic.of().setLowerBound("E"))
                    .add(Generic.of().setLowerBound("T")
                        .setBoundType(EXTENDS)
                        .add(SimpleParameterizedType.create(
                            Comparable.class, 
                            SimpleType.create("T")
                        ))
                    )
                    .add(Generic.of("F")
                        .setBoundType(EXTENDS)
                        .add(SimpleParameterizedType.create(
                            HasComparableOperators.class,
                            SimpleType.create("E"),
                            SimpleType.create("T")
                        ))
                    )
                    .add(Field.of("entityType", DefaultType.classOf(SimpleType.create("E"))))
                    .add(Field.of("field", SimpleType.create("F")))
                    .add(Field.of("view", SimpleParameterizedType.create(
                        MaterializedView.class,
                        SimpleType.create("E"),
                        SimpleType.create("T")
                    )))
                    .call(() -> file.add(Import.of(Consumer.class)))
                    .add(
                        "final Consumer<List<E>> con = view;",
                        "_reactors.add(Reactor.builder(managerOf(entityType), field)",
                        "    .withListener(con)",
                        "    .build());"
                    )
                );
            });
        });
    }
    
    private static Stream<Constructor> getOrCreate(Class clazz) {
        final List<Constructor> constructors = clazz.getConstructors();
        
        if (constructors.isEmpty()) {
            final Constructor constr = Constructor.of().protected_();
            clazz.add(constr);
            return Stream.of(constr);
        }
        
        return constructors.stream();
    }

    private static Method getOrCreate(Class clazz, String methodName) {
        return getOrCreate(clazz, methodName, void.class);
    }
    
    private static Method getOrCreate(Class clazz, String methodName, Type returnType) {
        return clazz.getMethods().stream()
            .filter(m -> m.getName().equals(methodName))
            .findAny().orElseGet(() -> {
                final Method method = Method.of(methodName, returnType)
                    .public_()
                    .add(OVERRIDE);
                
                clazz.add(method);
                return method;
            });
    }
}
