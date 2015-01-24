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
package com.speedment.codegen.view.java8;

import com.speedment.codegen.view.java.FieldView;
import com.speedment.codegen.view.java.JavaCodeGen;
import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.Type_;
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
public class FieldViewTest {
	
	private static CodeGenerator renderer;
	private FieldView view;
	
	public FieldViewTest() {
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
		view = new FieldView();
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of render method, of class FieldView.
	 */
	@Test
	public void testRender() {
		Field_ field = new Field_(Type_.DOUBLE_PRIMITIVE, "width").final_().private_().static_();
		CharSequence[] expResult = new CharSequence[] {
			"private final static double width",
			"private static final double width",
			"final private static double width",
			"final static private double width",
			"static private final double width",
			"static final private double width"
		};

		CharSequence result = view.render(renderer, field);
		
		boolean success = false;
		for (CharSequence exp : expResult) {
			if (exp.equals(result)) {
				success = true;
				break;
			}
		}
		
		assertTrue("FieldView.render gave an unexpected output of: '" + result + "'.", success);
	}
	
}
