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
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;

final class AutoConstructorTest extends AbstractControllerTest {

    @Override
    File createFile() {
        return File.of("com/example/Point.java")
                .add(Class.of("Point")
                        .public_()
                        .add(Field.of("x", int.class).private_().final_())
                        .add(Field.of("y", int.class).private_().final_())
                        .add(Field.of("label", String.class).private_().final_())
                        .call(new AutoConstructor()));
    }

    @Override
    String expected() {
        return "package com.example;\n" +
                "\n" +
                "import static java.util.Objects.requireNonNull;\n" +
                "\n" +
                "public class Point {\n" +
                "    \n" +
                "    private final int x;\n" +
                "    private final int y;\n" +
                "    private final String label;\n" +
                "    \n" +
                "    public Point(\n" +
                "            final int x,\n" +
                "            final int y,\n" +
                "            final String label) {\n" +
                "        this.x = x;\n" +
                "        this.y = y;\n" +
                "        this.label = requireNonNull(label);\n" +
                "    }\n" +
                "}";
    }

}