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
package com.speedment.common.codegen.examples;

import com.speedment.common.codegen.constant.DefaultJavadocTag;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.*;

import java.util.List;

final class InterfaceExampleTest extends AbstractExample {

    @Override
    void onFile(File file) {
        final Interface anInterface = Interface.of(simpleName())
                .public_()
                .set(Javadoc.of("This is a test interface.")
                        .add(DefaultJavadocTag.AUTHOR
                                        .setValue("tester")
                                        .setText("the one and only"))
                        .add(JavadocTag.of("apiNote")
                                .setValue("Use with care")
                                .setValue("always")
                        )
                )
                .add(SimpleParameterizedType.create(List.class, String.class))
                .add(Field.of("NAME", String.class).set(Value.ofText("John")))
                .add(Method.of("three", int.class).default_().add("return 3;"))
                .add(Method.of("render", Object.class).add(Field.of("model", Field.class)))
                .add(Method.of("create", SimpleType.create(simpleName())).static_().add("return null;"))
                .add(Import.of(List.class));

        file.add(anInterface);
    }

}