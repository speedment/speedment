package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;
import static com.speedment.codegen.view.java8.ClassAndInterfaceView.*;
import com.speedment.codegen.model.Interface_;

/**
 *
 * @author Duncan
 * @param <Model>
 */
public class JavaInterfaceView<Model extends Interface_> extends CodeView<Model> {
	@Override
	public CharSequence render(CodeGenerator renderer, Model interf) {
		return new $(
			renderPackage(renderer, interf), dnl(),
			renderModifiers(interf, renderer, SPACE),
			renderName(interf), SPACE,
			renderList(interf.getInterfaces(), renderer, COMMA_STRING, EXTENDS_STRING, SPACE),
			looseBracketsIndent(new $(
				renderList(interf.getFields(), renderer, nl()), dnl(),
				renderList(interf.getMethods(), renderer, dnl())
			))
		);
	}
}