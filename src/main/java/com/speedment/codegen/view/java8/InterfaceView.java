package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Interface_;
import com.speedment.codegen.model.modifier.InterfaceModifier_;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author Duncan
 */
public class InterfaceView extends ClassAndInterfaceView<InterfaceModifier_, Interface_> {

	public InterfaceView() {
		super (InterfaceModifier_.class, InterfaceModifier_.values());
	}
	
	@Override
	public CharSequence render(CodeGenerator renderer, Interface_ interf) {
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