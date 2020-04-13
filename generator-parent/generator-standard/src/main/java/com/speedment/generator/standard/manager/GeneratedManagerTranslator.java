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
package com.speedment.generator.standard.manager;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.core.manager.Manager;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultType.classOf;
import static com.speedment.common.codegen.constant.DefaultType.list;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 * @since 2.3.0
 */
public final class GeneratedManagerTranslator extends AbstractEntityAndManagerTranslator<Interface> {

    public GeneratedManagerTranslator(Injector injector, Table table) {
        super(injector, table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getSupport().generatedManagerName())
            .forEveryTable((intf, table) -> {

                file.add(Import.of(getSupport().entityType()));
                file.add(Import.of(Collections.class).setStaticMember("unmodifiableList").static_());
                file.add(Import.of(Arrays.class).setStaticMember("asList").static_());

                intf.public_()
                    .add(SimpleParameterizedType.create(Manager.class, getSupport().entityType()))
                    .add(
                        Field.of(
                            "IDENTIFIER",
                            SimpleParameterizedType.create(TableIdentifier.class, getSupport().entityType())
                        ).set(Value.ofInvocation(TableIdentifier.class, "of",
                            Stream.<HasAlias>of(
                                table.getParentOrThrow().getParentOrThrow(),
                                table.getParentOrThrow(),
                                table
                            ).map(HasName::getName)
                                .map(Value::ofText)
                                .toArray(Value[]::new)
                        ))
                    )
                    .add(Field.of("FIELDS", list(SimpleParameterizedType.create(
                        com.speedment.runtime.field.Field.class,
                        getSupport().entityType())
                    )).set(Value.ofReference("unmodifiableList(asList(" + Formatting.nl() + Formatting.indent(
                        table.columns()
                            .sorted(comparing(Column::getOrdinalPosition))
                            .filter(HasEnabled::isEnabled)
                            .map(Column::getJavaName)
                            .map(getSupport().namer()::javaStaticFieldName)
                            .map(field -> getSupport().typeName() + "." + field)
                            .collect(joining("," + Formatting.nl()))
                    ) + Formatting.nl() + "))")))
                    .add(Method.of("getEntityClass", classOf(getSupport().entityType()))
                        .default_().add(OVERRIDE)
                        .add("return " + getSupport().entityName() + ".class;")
                    );
            }).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base interface for the manager of every {@link "
            + getSupport().entityType().getTypeName() + "} entity.";
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
