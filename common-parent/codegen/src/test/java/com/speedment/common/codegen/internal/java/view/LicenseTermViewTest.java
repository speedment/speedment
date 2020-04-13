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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.LicenseTerm;
import com.speedment.common.codegen.util.Formatting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.speedment.common.codegen.util.Formatting.nl;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.1
 */
final class LicenseTermViewTest {

    private static final String licenseText = "Line 1" + nl() + "Line 2";
    private Generator generator;
    
    @BeforeEach
    void setup() {
        this.generator = new JavaGenerator();
        Formatting.tab("    ");
    }
    
    @Test
    void testMain() {
        final File file = File.of("com/example/Main.java").add(Class.of("Main").final_()).set(LicenseTerm.of(licenseText));
        
        final String expected =
            "/*\n" +
            " * Line 1\n" +
            " * Line 2\n" +
            " */\n" +
            "package com.example;\n" +
            "\n" +
            "final class Main {}";
        
        final String actual = generator.on(file).get();
        
/*
        System.out.println("------------------ Expected: --------------------");
        System.out.println(expected);
        System.out.println("------------------- Actual: ---------------------");
        System.out.println(actual);
        System.out.println("-------------------- End ------------------------");
*/
        assertEquals( expected, actual);
    }

}