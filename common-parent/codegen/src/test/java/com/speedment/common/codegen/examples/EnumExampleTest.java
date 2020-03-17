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
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Enum;

import java.util.List;

final class EnumExampleTest extends AbstractExample {

    @Override
    void onFile(File file) {
        final Enum anEnum = Enum.of(simpleName())
                .public_()
                .set(Javadoc.of("This is a test enum.").add(DefaultJavadocTag.AUTHOR.setValue("tester").setText("the one and only")))
                .add(SimpleParameterizedType.create(List.class, String.class))
                .add(Field.of("NAME", String.class).set(Value.ofText("John")).private_().static_().final_())
                .add(Method.of("three", int.class).default_().add("return 3;"))
                .add(Method.of("render", Object.class).add(Field.of("model", Field.class)).public_())
                .add(Method.of("create", SimpleType.create(simpleName())).static_().add("return null;"))
                .add(
                        EnumConstant.of("A")
                                .set(Javadoc.of("A constant"))
                                .add(Value.ofText("a"))
                                .add(Initializer.of().add("int a = 1;"))
                )
                .add(
                        EnumConstant.of("B")
                                .set(Javadoc.of("B constant"))
                                .add(Value.ofText("b"))
                                .add(Field.of("foo", int.class))
                ).add(
                        EnumConstant.of("C")
                                .set(Javadoc.of("C constant"))
                                .add(Value.ofText("c"))
                                .add(Class.of("Foo").private_().final_().static_())
                ).add(
                        EnumConstant.of("D")
                                .set(Javadoc.of("D constant"))
                                .add(Value.ofText("d"))
                )
                .add(Field.of("text", String.class).private_().final_())
                .add(Constructor.of().add(Field.of("text", String.class)).add("this.text = text;").private_())
                .add(Initializer.of().add("int a = 1;"))
                .add(Import.of(List.class));

        file.add(anEnum);
    }

}