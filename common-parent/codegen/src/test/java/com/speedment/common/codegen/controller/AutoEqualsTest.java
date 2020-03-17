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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AutoEqualsTest extends AbstractControllerTest {

    @Override
    File createFile() {
        final File file = File.of("com/example/Holder.java");

        return file
                .add(Class.of("Holder")
                        .public_()
                        .add(Field.of("b", byte.class).private_().final_())
                        .add(Field.of("s", short.class).private_().final_())
                        .add(Field.of("i", int.class).private_().final_())
                        .add(Field.of("l", long.class).private_().final_())
                        .add(Field.of("f", float.class).private_().final_())
                        .add(Field.of("d", double.class).private_().final_())
                        .add(Field.of("bool", boolean.class).private_().final_())
                        .add(Field.of("ch", char.class).private_().final_())
                        .add(Field.of("string", String.class).private_().final_())
                        .call(new AutoEquals<>(file)));
    }

    @Override
    String expected() {
        return "package com.example;\n" +
                "\n" +
                "import java.util.Objects;\n" +
                "import java.util.Optional;\n" +
                "\n" +
                "public class Holder {\n" +
                "    \n" +
                "    private final byte b;\n" +
                "    private final short s;\n" +
                "    private final int i;\n" +
                "    private final long l;\n" +
                "    private final float f;\n" +
                "    private final double d;\n" +
                "    private final boolean bool;\n" +
                "    private final char ch;\n" +
                "    private final String string;\n" +
                "    \n" +
                "    /**\n" +
                "     * Compares this object with the specified one for equality. The other\n" +
                "     * object must be of the same type and not null for the method to return\n" +
                "     * true.\n" +
                "     * \n" +
                "     * @param other The object to compare with.\n" +
                "     * @return      True if the objects are equal.\n" +
                "     */\n" +
                "    @Override\n" +
                "    public boolean equals(Object other) {\n" +
                "        return Optional.ofNullable(other)\n" +
                "            .filter(o -> getClass().equals(o.getClass()))\n" +
                "            .map(o -> (Holder) o)\n" +
                "            .filter(o -> (this.b == o.b))\n" +
                "            .filter(o -> (this.s == o.s))\n" +
                "            .filter(o -> (this.i == o.i))\n" +
                "            .filter(o -> (this.l == o.l))\n" +
                "            .filter(o -> (this.f == o.f))\n" +
                "            .filter(o -> (this.d == o.d))\n" +
                "            .filter(o -> (this.bool == o.bool))\n" +
                "            .filter(o -> (this.ch == o.ch))\n" +
                "            .filter(o -> Objects.equals(this.string, o.string))\n" +
                "            .isPresent();\n" +
                "    }\n" +
                "    \n" +
                "    /**\n" +
                "     * Generates a hashCode for this object. If any field is changed to another\n" +
                "     * value, the hashCode may be different. Two objects with the same values\n" +
                "     * are guaranteed to have the same hashCode. Two objects with the same\n" +
                "     * hashCode are not guaranteed to have the same hashCode.\n" +
                "     * \n" +
                "     * @return The hash code.\n" +
                "     */\n" +
                "    @Override\n" +
                "    public int hashCode() {\n" +
                "        int hash = 7;\n" +
                "        hash = 31 * hash + (Byte.hashCode(this.b));\n" +
                "        hash = 31 * hash + (Short.hashCode(this.s));\n" +
                "        hash = 31 * hash + (Integer.hashCode(this.i));\n" +
                "        hash = 31 * hash + (Long.hashCode(this.l));\n" +
                "        hash = 31 * hash + (Float.hashCode(this.f));\n" +
                "        hash = 31 * hash + (Double.hashCode(this.d));\n" +
                "        hash = 31 * hash + (Boolean.hashCode(this.bool));\n" +
                "        hash = 31 * hash + (Character.hashCode(this.ch));\n" +
                "        hash = 31 * hash + (Objects.hashCode(this.string));\n" +
                "        return hash;\n" +
                "    }\n" +
                "}";
    }
}