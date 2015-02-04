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
package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.statement.Statement_;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Duncan
 */
public class StatementViewTest {

    private StatementView view;
    private static CodeGenerator renderer;

    public StatementViewTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        renderer = new JavaCodeGen();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        view = new StatementView();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of render method, of class StatementView.
     */
    @Test
    public void testRender() {
        CharSequence expResult = "System.out.println(\"Hello, Speedment!\");";

        Statement_ statement = Statement_.of("System.out.println(\"Hello, Speedment!\");");

        Optional<CharSequence> result = view.render(renderer, statement);

        assertTrue("StatementView.render did not return any output.", result.isPresent());
        assertEquals(expResult, result.get());
    }

}
