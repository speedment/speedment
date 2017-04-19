/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.*;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.manager.Manager;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 */
public final class GeneratedManagerTranslator 
extends AbstractEntityAndManagerTranslator<Interface> {

    public GeneratedManagerTranslator(Table table) {
        super(table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getSupport().generatedManagerName())
            .forEveryTable((intf, table) -> {
                intf.public_()
                    .add(SimpleParameterizedType.create(Manager.class, getSupport().entityType()))
                    .add(Method.of("getEntityClass", DefaultType.classOf(getSupport().entityType())).default_().add(OVERRIDE)
                        .add("return " + getSupport().entityName() + ".class;")
                    );
            }).build();
    }

    protected Method generatePrimaryKeyFor(File file) {
        final Method method = Method.of("primaryKeyFor", typeOfPK()).default_().add(OVERRIDE)
            .add(Field.of("entity", getSupport().entityType()));

        if (primaryKeyColumns().count() == 1) {
            method.add("return entity.get" + getSupport().typeName(primaryKeyColumns().findAny().get().findColumn().get()) + "();");
        } else {
            file.add(Import.of(Arrays.class));
            method.add(primaryKeyColumns()
                .sorted(Comparator.comparing(PrimaryKeyColumn::getOrdinalPosition))
                .map(pkc -> "entity.get" + getSupport().typeName(pkc.findColumn().get()) + "()")
                .collect(Collectors.joining(", ", "return Arrays.asList(", ");"))
            );
        }

        return method;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base interface for the manager of every {@link " + 
            getSupport().entityType().getTypeName() + "} entity.";
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