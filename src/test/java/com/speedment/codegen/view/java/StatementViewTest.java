package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Statement_;
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
	
	public StatementViewTest() {}
	
	@BeforeClass
	public static void setUpClass() {
		renderer = new JavaCodeGen();
	}
	
	@AfterClass
	public static void tearDownClass() {}
	
	@Before
	public void setUp() {
		view = new StatementView();
	}
	
	@After
	public void tearDown() {}

	/**
	 * Test of render method, of class StatementView.
	 */
	@Test
	public void testRender() {
		CharSequence expResult = "System.out.println(\"Hello, Speedment!\");";
		
		Statement_ statement = new Statement_("System.out.println(\"Hello, Speedment!\");");
		
		CharSequence result = view.render(renderer, statement);
		assertEquals(expResult, result);
	}
	
}
