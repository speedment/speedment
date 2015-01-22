package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.CodeModel;

/**
 *
 * @author Duncan
 */
public class JavaCodeGen extends CodeGenerator {
	public JavaCodeGen() {
		super ((CodeModel.Type type) -> {
			switch (type) {
				case BLOCK			: return new JavaBlockView();
				case CLASS			: return new JavaClassView();
				case CONSTRUCTOR	: return new JavaConstructorView();
				case EXPRESSION		: return new JavaExpressionView();
				case FIELD			: return new JavaFieldView();
				case STATEMENT		: return new JavaStatementView();
				case METHOD			: return new JavaMethodView();
				case TYPE			: return new JavaTypeView();
				default : throw new UnsupportedOperationException("Missing implementation for type " + type + ".");
			}
		});
	}
}