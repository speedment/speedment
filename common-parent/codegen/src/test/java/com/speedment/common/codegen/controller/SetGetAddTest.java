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

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.speedment.common.codegen.constant.DefaultType.WILDCARD;
import static org.junit.jupiter.api.Assertions.*;

final class SetGetAddTest extends AbstractControllerTest {

    private static final SimpleType T = SimpleType.create("T");

    @Override
    File createFile() {
        return File.of("com/example/Holder.java")
                .add(Class.of("Holder")
                        .public_()
                        .final_()
                        .add(Generic.of(T))
                        .add(Field.of("x", int.class))
                        .add(Field.of("names", SimpleParameterizedType.create(List.class, String.class)))
                        .add(Field.of("abilities", SimpleParameterizedType.create(List.class, String.class)))
                        .add(Field.of("list", SimpleParameterizedType.create(List.class, String.class)))
                        .add(Field.of("optional", SimpleParameterizedType.create(Optional.class, T)))
                )
                .call(new SetGetAdd());
    }

    @Override
    String expected() {
        return "package com.example;\n" +
                "\n" +
                "public final class Holder<T> {\n" +
                "    \n" +
                "    private int x;\n" +
                "    private final java.util.List<String> names;\n" +
                "    private final java.util.List<String> abilities;\n" +
                "    private final java.util.List<String> list;\n" +
                "    private java.util.Optional<T> optional;\n" +
                "    \n" +
                "    /**\n" +
                "     * Sets the x of this Holder.\n" +
                "     * \n" +
                "     * @param x the new value\n" +
                "     * @return  a reference to this object\n" +
                "     */\n" +
                "    public Holder<T> setX(int x) {\n" +
                "        this.x = x;\n" +
                "        return this;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Gets the x of this Holder.\n" +
                "     * \n" +
                "     * @return the x\n" +
                "     */\n" +
                "    public int getX() {\n" +
                "        return this.x;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Adds the specified string to the names of this Holder.\n" +
                "     * \n" +
                "     * @param name the new value\n" +
                "     * @return     a reference to this object\n" +
                "     */\n" +
                "    public Holder<T> addToNames(String name) {\n" +
                "        this.names.add(name);\n" +
                "        return this;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Gets the names of this Holder.\n" +
                "     * \n" +
                "     * @return the names\n" +
                "     */\n" +
                "    public java.util.List<String> getNames() {\n" +
                "        return this.names;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Adds the specified string to the abilities of this Holder.\n" +
                "     * \n" +
                "     * @param ability the new value\n" +
                "     * @return        a reference to this object\n" +
                "     */\n" +
                "    public Holder<T> addToAbilities(String ability) {\n" +
                "        this.abilities.add(ability);\n" +
                "        return this;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Gets the abilities of this Holder.\n" +
                "     * \n" +
                "     * @return the abilities\n" +
                "     */\n" +
                "    public java.util.List<String> getAbilities() {\n" +
                "        return this.abilities;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Adds the specified string to the list of this Holder.\n" +
                "     * \n" +
                "     * @param list the new value\n" +
                "     * @return     a reference to this object\n" +
                "     */\n" +
                "    public Holder<T> addToList(String list) {\n" +
                "        this.list.add(list);\n" +
                "        return this;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Gets the list of this Holder.\n" +
                "     * \n" +
                "     * @return the list\n" +
                "     */\n" +
                "    public java.util.List<String> getList() {\n" +
                "        return this.list;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Sets the optional of this Holder.\n" +
                "     * \n" +
                "     * @param optional the new value\n" +
                "     * @return         a reference to this object\n" +
                "     */\n" +
                "    public Holder<T> setOptional(java.util.Optional<T> optional) {\n" +
                "        this.optional = optional;\n" +
                "        return this;\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Gets the optional of this Holder.\n" +
                "     * \n" +
                "     * @return the optional\n" +
                "     */\n" +
                "    public java.util.Optional<T> getOptional() {\n" +
                "        return this.optional;\n" +
                "    }\n" +
                "}";
    }
}