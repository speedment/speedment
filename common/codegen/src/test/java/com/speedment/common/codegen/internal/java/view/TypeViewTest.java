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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.controller.AutoImports;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.internal.model.value.TextValue;
import com.speedment.common.codegen.internal.util.Formatting;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.1
 */
public class TypeViewTest {

    private Generator generator;
    
    @Before
    public void setup() {
        this.generator = new JavaGenerator();
        Formatting.tab("    ");
    }
    
    @Test
    public void testMain() {
        final File file = File.of("com/example/Main.java")
            .add(Class.of("Main")
                .public_().final_()
                .add(Field.of("MESSAGE", String.class)
                    .public_().final_().static_()
                    .set(new TextValue("Hello, World!"))
                )
                .add(Method.of("main", void.class)
                    .public_().static_()
                    .add(Field.of("params", String[].class))
                    .add("System.out.println(MESSAGE);")
                )
            );
        
        final String expected =
            "package com.example;\n" +
            "\n" +
            "public final class Main {\n" +
            "    \n" +
            "    public final static String MESSAGE = \"Hello, World!\";\n" +
            "    \n" +
            "    public static void main(String[] params) {\n" +
            "        System.out.println(MESSAGE);\n" +
            "    }\n" +
            "}";
        
        final String actual = generator.on(file).get();
        
        System.out.println("------------------ Expected: --------------------");
        System.out.println(expected);
        System.out.println("------------------- Actual: ---------------------");
        System.out.println(actual);
        System.out.println("-------------------- End ------------------------");
        
        Assert.assertEquals("Make sure generated file matches expected:", expected, actual);
    }
    
    @Test
    public void testList() {
        final File file = File.of("com/example/EntityList.java")
            .add(Import.of(ArrayList.class))
            .add(Import.of(Objects.class).static_().setStaticMember("requireNonNull"))
            .add(Class.of("EntityList")
                .public_().final_()
                .add(Generic.of("E").add(SimpleType.create("com.example.entity.Entity")))
                .add(Field.of("entities", DefaultType.list(SimpleType.create("E")))
                    .private_().final_()
                )
                .add(Constructor.of()
                    .public_()
                    .add(Field.of("entities", SimpleParameterizedType.create(
                        List.class,
                        SimpleType.create("E")
                    )))
                    .add("this.entities = new ArrayList<>(entities);")
                )
                .add(Method.of("add", void.class)
                    .public_()
                    .add(Field.of("entity", SimpleType.create("E")))
                    .add("requireNonNull(entity);")
                    .add("entities.add(entity);")
                )
                .add(Method.of("size", int.class)
                    .public_()
                    .add("return entities.size();")
                )
            ).call(new AutoImports(generator.getDependencyMgr()));
        
        final String expected =
            "package com.example;\n" +
            "\n" +
            "import com.example.entity.Entity;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.List;\n" +
            "import static java.util.Objects.requireNonNull;\n" +
            "\n" +
            "public final class EntityList<E extends Entity> {\n" +
            "    \n" +
            "    private final List<E> entities;\n" +
            "    \n" +
            "    public EntityList(List<E> entities) {\n" +
            "        this.entities = new ArrayList<>(entities);\n" +
            "    }\n" +
            "    \n" +
            "    public void add(E entity) {\n" +
            "        requireNonNull(entity);\n" +
            "        entities.add(entity);\n" +
            "    }\n" +
            "    \n" +
            "    public int size() {\n" +
            "        return entities.size();\n" +
            "    }\n" +
            "}";
        
        final String actual = generator.on(file).get();
        
        System.out.println("------------------ Expected: --------------------");
        System.out.println(expected);
        System.out.println("------------------- Actual: ---------------------");
        System.out.println(actual);
        System.out.println("-------------------- End ------------------------");
        
        Assert.assertEquals("Make sure generated file matches expected:", expected, actual);
    }
}