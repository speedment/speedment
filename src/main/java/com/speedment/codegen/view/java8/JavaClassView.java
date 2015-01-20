package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Class_;
import com.speedment.codegen.model.Package_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import static com.speedment.codegen.model.CodeModel.Type.*;

/**
 *
 * @author Duncan
 */
public class JavaClassView extends CodeView<Class_> {
	@Override
	public CharSequence render(CodeGenerator renderer, Class_ clazz) {
		return new $(
			"package ", 
			((Package_) renderer.last(PACKAGE)).getName_(), 
			";", NL, NL
			// TODO: Imports.
			
		);
	}
}