package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.util.Formatting;

final class AlignTabsTest extends AbstractControllerTest {

    @Override
    File createFile() {
        return File.of("com/example/Point.java")
                .add(Class.of("Point")
                        .public_()
                        .add(Field.of("x", int.class))
                        .add(Method.of("x", void.class)
                                .add(Field.of("x", int.class))
                                .add("this.x\t= x;")
                                .add("this.someotherlonger\t= x;")
                                .set(Javadoc.of("a\t1"+ Formatting.nl()+"abcdefghijklm\t2"))
                        )

                        .call(new AlignTabs<>()));
    }

    @Override
    String expected() {
        return "package com.example;\n" +
                "\n" +
                "public class Point {\n" +
                "    \n" +
                "    int x;\n" +
                "    \n" +
                "    /**\n" +
                "     * a            1\n" +
                "     * abcdefghijklm2\n" +
                "     */\n" +
                "    void x(int x) {\n" +
                "        this.x              = x;\n" +
                "        this.someotherlonger= x;\n" +
                "    }\n" +
                "}";
    }


}