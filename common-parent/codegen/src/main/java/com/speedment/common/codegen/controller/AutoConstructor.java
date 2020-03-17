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
package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;

import java.util.Objects;
import java.util.function.Consumer;

import static com.speedment.common.codegen.constant.DefaultType.isPrimitive;
import static com.speedment.common.codegen.model.modifier.Modifier.FINAL;
import static java.lang.String.format;

/**
 * Generates a public constructor with all the final fields as input
 * parameters.
 *
 * @author Emil Forslund
 * @since  2.5
 */
public final class AutoConstructor implements Consumer<Class> {

    @Override
    public void accept(Class aClass) {
        aClass.add(Constructor.newPublic()
            .call(constr -> aClass.getFields().stream()
                .filter(f -> f.getModifiers().contains(FINAL))
                .map(Field::copy)
                .forEachOrdered(f -> {
                    f.getModifiers().clear();
                    f.final_();
                    constr.add(f).imports(Objects.class, "requireNonNull");
                    if (isPrimitive(f.getType())) {
                        constr.add(format("this.%1$s = %1$s;", f.getName()));
                    } else {
                        constr.add(format("this.%1$s = requireNonNull(%1$s);", f.getName()));
                    }
                })
            )
        );
    }
}
