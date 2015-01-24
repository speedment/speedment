package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.dependency_.Dependency_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author Duncan
 */
public class DependencyView extends CodeView<Dependency_> {
	private final static String 
		IMPORT_STRING = "import ", 
		STATIC_STRING = "static ";
	
	@Override
	public CharSequence render(CodeGenerator renderer, Dependency_ model) {
		return new $(
			IMPORT_STRING, 
			model.isStatic() ? STATIC_STRING : EMPTY, 
			model.getSource(),
			SC
		);
	}
}
