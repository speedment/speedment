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