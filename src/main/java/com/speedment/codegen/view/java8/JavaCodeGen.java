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
				case ANNOTATION		: throw new UnsupportedOperationException("Missing implementation for type " + type + ".");
				case BLOCK			: return new BlockView();
				case CLASS			: return new ClassView();
				case CONSTRUCTOR	: return new ConstructorView();
				case EXPRESSION		: return new ExpressionView();
				case FIELD			: return new FieldView();
				case INTERFACE		: return new InterfaceView();
				case METHOD			: return new MethodView();
				case OPERATOR		: throw new UnsupportedOperationException("Missing implementation for type " + type + ".");
				case PACKAGE		: return new PackageView();
				case STATEMENT		: return new StatementView();
				case TYPE			: return new TypeView();
				default : throw new UnsupportedOperationException("Missing implementation for type " + type + ".");
			}
		});
	}
}