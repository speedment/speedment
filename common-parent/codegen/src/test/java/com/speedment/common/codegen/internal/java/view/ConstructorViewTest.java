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
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Javadoc;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.SUPPRESS_WARNINGS_UNCHECKED;
import static com.speedment.common.codegen.util.Formatting.block;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Emil Forslund
 * @since  2.4.10
 */
final class ConstructorViewTest {

    @Test
    void transform() {
        final File file = File.of("com/example/Main.java")
            .add(Import.of(StringBuilder.class))
            .add(Class.of("Main").public_().final_()
                .add(Field.of("string", String.class).private_().final_())
                .add(Constructor.of()
                    .public_()
                    .add(SUPPRESS_WARNINGS_UNCHECKED)
                    .add(IllegalArgumentException.class)
                    .set(Javadoc.of("The constructor for this class."))
                    .add(Field.of("stringOrBuilder", CharSequence.class))
                    .add("if (stringOrBuilder instanceof String) " + block(
                        "this.string = (String) stringOrBuilder;"
                    ) + " else if () " + block(
                        "this.string = ((StringBuilder) stringOrBuilder).toString();"
                    ) + " else " + block(
                        "throw new IllegalArgumentException(\"Not a String or StringBuilder!\");"
                    ))
                )
            );

        final Generator generator = new JavaGenerator();
        final String code = generator.on(file).get();

        assertNotNull(code);

        Stream.of(
            "package com.example;",
            "public final class Main",
            "public Main(",
            String.class.getSimpleName(),
            StringBuilder.class.getSimpleName(),
            "Main",
            "public",
            "final",
            "private"
        ).forEach(s -> {
            assertTrue(code.contains(s));
        });

    }
}