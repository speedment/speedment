package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Method;

final class FinalParametersTest extends  AbstractControllerTest {

    @Override
    File createFile() {
        return File.of("com/example/Point.java")
                .add(Class.of("Point")
                        .public_()
                        .add(Field.of("x", int.class))
                        .add(Field.of("y", int.class))
                        .add(Method.of("x", void.class)
                                .add(Field.of("x", int.class))
                                .add("this.x = x;")
                        )
                .call(new FinalParameters<>()));
    }

    @Override
    String expected() {
        return "package com.example;\n" +
                "\n" +
                "public class Point {\n" +
                "    \n" +
                "    int x;\n" +
                "    int y;\n" +
                "    \n" +
                "    void x(final int x) {\n" +
                "        this.x = x;\n" +
                "    }\n" +
                "}";
    }
}