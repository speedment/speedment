/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.java.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.util.Formatting;
import static com.speedment.common.codegen.util.Formatting.nl;
import java.util.Objects;
import java.util.Optional;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Emil Forslund
 * @since  2.4.4
 */
public class HasImportsViewTest {
    
    private Generator gen;
    private HasImportsView<File> view;
    private File file;
    
    @Before
    public void setUp() {
        gen  = new JavaGenerator();
        view = new MockImportsFile();
        file = File.of("com/example/TestFile.java");
        file.add(Import.of(Objects.class));
        file.add(Import.of(Objects.class).static_().setStaticMember("requireNonNull"));
        file.add(Import.of(Objects.class).static_().setStaticMember("equals"));
    }
    
    @Test
    public void makeSureAllImportsArePresent() {
        final Import[] expected = {
            Import.of(Objects.class),
            Import.of(Objects.class).static_().setStaticMember("requireNonNull"),
            Import.of(Objects.class).static_().setStaticMember("equals")
        };
        
        final Import[] actual = file.getImports().stream().toArray(Import[]::new);
        
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testRenderImports() {
        final String expected = Formatting.separate(nl(), 
            "import java.util.Objects;",
            "import static java.util.Objects.equals;", // <-- Order alphabetically.
            "import static java.util.Objects.requireNonNull;",
            "", // <-- Finish with an empty line.
            ""  // <-- Generation continues on here.
        );
        
        final String actual = view.transform(gen, file).orElse(null);
        
        try { assertEquals(expected, actual); }
        catch (final Throwable thrw) {
            System.out.println("------------------ Expected -----------------");
            System.out.println(expected);
            System.out.println("------------------- Actual ------------------");
            System.out.println(actual);
            System.out.println("---------------------------------------------");
            throw thrw;
        }
    }
    
    private final class MockImportsFile implements HasImportsView<File> {
        @Override
        public Optional<String> transform(Generator gen, File model) {
            return Optional.of(renderImports(gen, model));
        }
    }
}