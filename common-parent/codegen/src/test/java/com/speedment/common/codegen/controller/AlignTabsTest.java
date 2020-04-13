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
                                .add("this.x\t= x;\t// Comment")
                                .add("this.someotherlonger\t= someOtherEvenLongerValue;\t// Comment")
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
                "        this.x              = x;                       // Comment\n" +
                "        this.someotherlonger= someOtherEvenLongerValue;// Comment\n" +
                "    }\n" +
                "}";
    }

}