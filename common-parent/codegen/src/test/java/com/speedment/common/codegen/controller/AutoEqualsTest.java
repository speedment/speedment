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
                "public class Point {\n" +
                "    \n" +
                "    private final int x;\n" +
                "    private final int y;\n" +
                "    private final String label;\n" +
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
                "            .map(o -> (Point) o)\n" +
                "            .filter(o -> (this.x == o.x))\n" +
                "            .filter(o -> (this.y == o.y))\n" +
                "            .filter(o -> Objects.equals(this.label, o.label))\n" +
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
                "        hash = 31 * hash + (Integer.hashCode(this.x));\n" +
                "        hash = 31 * hash + (Integer.hashCode(this.y));\n" +
                "        hash = 31 * hash + (Objects.hashCode(this.label));\n" +
                "        return hash;\n" +
                "    }\n" +
                "}";
    }
}