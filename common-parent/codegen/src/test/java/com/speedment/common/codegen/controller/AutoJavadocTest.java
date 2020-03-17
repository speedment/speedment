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

import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class AutoJavadocTest extends AbstractControllerTest {

    @Override
    File createFile() {
        return File.of("com/example/EntityList.java")
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
                ).call(new AutoJavadoc<>());
    }

    @Override
    String expected() {
        return         "/**\n" +
                " * Write some documentation here.\n" +
                " */\n" +
                "package com.example;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "\n" +
                "import static java.util.Objects.requireNonNull;\n" +
                "\n" +
                "/**\n" +
                " * Write some documentation here.\n" +
                " * \n" +
                " * @param <E>\n" +
                " * \n" +
                " * @author Your Name\n" +
                " */\n" +
                "public final class EntityList<E extends Entity> {\n" +
                "    \n" +
                "    private final java.util.List<E> entities;\n" +
                "    \n" +
                "    /**\n" +
                "     * Write some documentation here.\n" +
                "     * \n" +
                "     * @param entities\n" +
                "     */\n" +
                "    public EntityList(java.util.List<E> entities) {\n" +
                "        this.entities = new ArrayList<>(entities);\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Write some documentation here.\n" +
                "     * \n" +
                "     * @param entity\n" +
                "     */\n" +
                "    public void add(E entity) {\n" +
                "        requireNonNull(entity);\n" +
                "        entities.add(entity);\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Write some documentation here.\n" +
                "     * \n" +
                "     * @return \n" +
                "     */\n" +
                "    public int size() {\n" +
                "        return entities.size();\n" +
                "    }\n" +
                "}";
    }
}