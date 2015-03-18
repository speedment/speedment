/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.codegen.examples;

import com.speedment.codegen.Formatting;
import com.speedment.codegen.java.JavaGenerator;
import com.speedment.codegen.lang.controller.AutoJavadoc;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.JavadocTag;
import com.speedment.codegen.lang.models.Method;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.GENERATED;
import com.speedment.codegen.lang.models.constants.DefaultType;
import com.speedment.codegen.lang.models.values.TextValue;

/**
 *
 * @author Emil Forslund
 */
public class BasicExample {
    public static void main(String... params) {
        Formatting.tab("    ");
        
        System.out.println(new JavaGenerator().on(File.of("org/example/BasicExample.java")
                .add(Class.of("BasicExample")
                    .set(Javadoc.of("").add(JavadocTag.of("author", "Your name")))
                    .add(GENERATED)
                    .public_()
                    .add(Field.of("BASIC_MESSAGE", DefaultType.STRING)
                        .public_().final_().static_()
                        .set(new TextValue("Hello, world!"))
                    )
                    .add(Method.of("main", DefaultType.VOID)
                        .set(Javadoc.of(
                            "This is a vary basic example of ",
                            "the capabilities of the Code Generator."
                        ))
                        .public_().static_()
                        .add(Field.of("params", DefaultType.STRING.setArrayDimension(1)))
                        .add(
                            "System.out.println(BASIC_MESSAGE);"
                        )
                    )
                ).call(new AutoJavadoc<>())
            ).get()
        );
    }
}
