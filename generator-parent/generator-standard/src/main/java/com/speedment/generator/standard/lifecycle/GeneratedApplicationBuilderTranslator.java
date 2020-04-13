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
package com.speedment.generator.standard.lifecycle;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.mapstream.MapStream;
import com.speedment.generator.translator.AbstractJavaClassTranslator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.application.AbstractApplicationBuilder;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.connector.mariadb.MariaDbBundle;
import com.speedment.runtime.connector.mysql.MySqlBundle;
import com.speedment.runtime.connector.postgres.PostgresBundle;
import com.speedment.runtime.connector.sqlite.SqliteBundle;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.AUTHOR;
import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.generator.standard.lifecycle.GeneratedMetadataTranslator.METADATA;
import static com.speedment.runtime.config.util.DocumentDbUtil.traverseOver;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;

/**
 *
 * @author Per Minborg
 * @since 2.4.0
 */
public final class GeneratedApplicationBuilderTranslator extends AbstractJavaClassTranslator<Project, Class> {

    private static final String CLASS = "class";

    public GeneratedApplicationBuilderTranslator(Injector injector, Project doc) {
        super(injector, doc, Class::of);
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);

        return newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, project) -> {

                final Map<String, List<Table>> nameMap = traverseOver(project, Table.class)
                    .filter(HasEnabled::test)
                    .collect(Collectors.groupingBy(Table::getId));

                final Set<String> ambiguousNames = MapStream.of(nameMap)
                    .filterValue(l -> l.size() > 1)
                    .keys()
                    .collect(toSet());

                final List<String> managerImpls = new LinkedList<>();
                final List<String> sqlAdapters = new LinkedList<>();

                traverseOver(project, Table.class)
                    .filter(HasEnabled::test)
                    .forEachOrdered(t -> {
                        final TranslatorSupport<Table> support = new TranslatorSupport<>(injector(), t);
                        final Type managerImplType = support.managerImplType();
                        final Type sqlAdapterType = support.sqlAdapterType();

                        if (ambiguousNames.contains(t.getId())) {
                            managerImpls.add(managerImplType.getTypeName());
                            sqlAdapters.add(sqlAdapterType.getTypeName());
                        } else {
                            file.add(Import.of(managerImplType));
                            file.add(Import.of(sqlAdapterType));
                            managerImpls.add(shortName(managerImplType.getTypeName()));
                            sqlAdapters.add(shortName(sqlAdapterType.getTypeName()));
                        }
                    });

                file.add(Import.of(applicationType()));
                file.add(Import.of(applicationImplType()));

                final Method build = Method.of("build", applicationType())
                    .public_().add(OVERRIDE)
                    .add(Field.of("injector", Injector.class))
                    .add("return injector.getOrThrow(" + getSupport().typeName(getSupport().projectOrThrow()) + "Application."+ CLASS +");");

                final Constructor constr = Constructor.of().protected_();

                final StringBuilder constructorBody = new StringBuilder("super(");
                constructorBody.append(getSupport().typeName(getSupport().projectOrThrow())).append("ApplicationImpl." + CLASS + ", ");
                constructorBody.append("Generated").append(getSupport().typeName(getSupport().projectOrThrow())).append(METADATA).append("." + CLASS + ");").append(Formatting.nl());

                final String separator = Formatting.nl();
                if (!managerImpls.isEmpty()) {

                    constructorBody.append(
                        managerImpls.stream()
                        .map(s -> "withManager(" + s + "." + CLASS + ");")
                        .collect(joining(separator, "", separator))
                    );
                    
                }

                if (!sqlAdapters.isEmpty()) {

                    constructorBody.append(
                        sqlAdapters.stream()
                        .map(s -> "withComponent(" + s + "." + CLASS + ");")
                        .collect(joining(separator))
                    );
                }

                databaseBundleClassNames(project)
                    .map(cn -> Formatting.nl() + "withBundle(" + shortName(cn) + "." + CLASS + ");")
                    .forEach(constructorBody::append);

                databaseBundleClassNames(project)
                    .map(cn -> Import.of(SimpleType.create(cn)))
                    .forEach(file::add);

                final Type injectorProxyType = injectorProxyType();
                constructorBody.append(Formatting.nl()).append("withInjectorProxy(new ").append(shortName(injectorProxyType.getTypeName())).append("());");
                file.add(Import.of(injectorProxyType()));

                constr.add(constructorBody.toString());

                clazz.public_().abstract_()
                    .setSupertype(
                        SimpleParameterizedType.create(
                            AbstractApplicationBuilder.class,
                            applicationType(),
                            builderType()
                        )
                    )
                    .add(constr)
                    .add(build);
            }).build();
    }

    @Override
    protected Javadoc getJavaDoc() {
        final String owner = infoComponent().getTitle();
        return Javadoc.of(getJavadocRepresentText() + getGeneratedJavadocMessage())
            .add(AUTHOR.setValue(owner));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A generated base {@link " + AbstractApplicationBuilder.class.getName()
            + "} class for the {@link " + Project.class.getName()
            + "} named " + getSupport().projectOrThrow().getId() + ".";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationBuilder";
    }

    private Type builderType() {
        return SimpleType.create(
            getSupport().basePackageName() + "."
            + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationBuilder"
        );
    }

    private Type applicationType() {
        return SimpleType.create(
            getSupport().basePackageName() + "."
            + getSupport().typeName(getSupport().projectOrThrow()) + "Application"
        );
    }

    private Type applicationImplType() {
        return SimpleType.create(
            getSupport().basePackageName() + "."
            + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationImpl"
        );
    }

    private Type injectorProxyType() {
        return SimpleType.create(
            getSupport().basePackageName() + "."
                + getSupport().typeName(getSupport().projectOrThrow()) + "InjectorProxy"
        );
    }

    private Stream<String> databaseBundleClassNames(Project project) {
        return traverseOver(project, Dbms.class)
            .filter(HasEnabled::test)
            .map(Dbms::getTypeName)
            .map(this::toBundleClassName)
            .filter(Optional::isPresent)
            .map(Optional::get);
    }

    private Optional<String> toBundleClassName(String typeName) {
        final Map<String, java.lang.Class<? extends InjectBundle>> bundles = Stream.of(
            MySqlBundle.class,
            MariaDbBundle.class,
            PostgresBundle.class,
            SqliteBundle.class
        ).collect(toMap(
            this::stripBundle,
            Function.identity()
        ));

        return bundles.entrySet().stream()
            .filter(e -> typeName.toLowerCase().contains(e.getKey().toLowerCase())) // Name magic...  :-(
            .map(Map.Entry::getValue)
            .map(java.lang.Class::getName)
            .findAny();
    }

    private String stripBundle(java.lang.Class<? extends InjectBundle> clazz) {
        final String simpleName = clazz.getSimpleName();
        final int lastIndex = simpleName.lastIndexOf("Bundle");

        if (lastIndex == -1) {
            throw new SpeedmentException("The class " + clazz + " does not contain a substring 'Bundle'");
        }
        return simpleName.substring(0, lastIndex);
    }


}
