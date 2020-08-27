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

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.controller.beta.Foo;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Value;

import java.util.Map;

class AutoImportsTest extends AbstractControllerTest {

    private static final Generator generator = Generator.forJava();

    @Override
    File createFile() {
        return File.of("com/example/DateMapper.java")
                .add(Class.of("FooClass").public_()
                    .add(Field.of("utilDateMap", SimpleParameterizedType.create(Map.class, String.class, Foo.class))
                        .private_().static_().final_()
                        .set(Value.ofReference("new HashMap<>()")))
                    .add(Field.of("sqlDateMap",  SimpleParameterizedType.create(Map.class, String.class, com.speedment.common.codegen.controller.alpha.Foo.class))
                        .private_().static_().final_()
                        .set(Value.ofReference("new HashMap<>()"))))
                    .call(new AutoImports(generator.getDependencyMgr()))
                .call(new AlignTabs<>());
    }

    @Override
    String expected() {
        return "package com.example;\n" +
                "\n" +
                "import com.speedment.common.codegen.controller.beta.Foo;\n\n" +
                "import java.util.Map;\n" +
                "\n" +
                "public class FooClass {\n" +
                "    \n" +
                "    private static final Map<String, Foo> utilDateMap = new HashMap<>();\n" +
                "    private static final Map<String, com.speedment.common.codegen.controller.alpha.Foo> sqlDateMap = new HashMap<>();\n" +
                "}";
    }
}