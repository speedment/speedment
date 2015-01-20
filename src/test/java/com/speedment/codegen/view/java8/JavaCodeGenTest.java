package com.speedment.codegen.view.java8;

import com.speedment.codegen.model.CodeModel;
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
public class JavaCodeGenTest {
	
	private JavaCodeGen inst;
	
	public JavaCodeGenTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		inst = new JavaCodeGen();
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testConstructor() {
		assertEquals(CodeModel.Type.values().length, inst.generatorsCount());
		fail("The test case is a prototype.");
	}
}
