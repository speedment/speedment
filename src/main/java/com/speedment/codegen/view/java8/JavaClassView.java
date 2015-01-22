package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Class_;
import com.speedment.util.$;
import com.speedment.codegen.model.modifier.ClassModifier_;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author Duncan
 */
public class JavaClassView extends ClassAndInterfaceView<ClassModifier_, Class_> {
	private final static String IMPLEMENTS_STRING = "implements ";

	public JavaClassView() {
		super(ClassModifier_.class, ClassModifier_.values());
	}

	private CharSequence renderParent(Class_ child) {
		return child.getSuperClass() == null ? EMPTY : 
			new $(EXTENDS_STRING, child.getSuperClass().getName(), SPACE);
	}

	@Override
	public CharSequence render(CodeGenerator renderer, Class_ model) {
		return new $(
			renderPackage(renderer, model), dnl(),
			renderModifiers(model, renderer, SPACE),
			renderName(model), SPACE,
			renderParent(model),
			renderList(model.getInterfaces(), renderer, COMMA_STRING, IMPLEMENTS_STRING, SPACE),
			looseBracketsIndent(new $(
				renderList(model.getFields(), renderer, nl()), dnl(),
				renderList(model.getConstructors(), renderer, nl()), dnl(),
				renderList(model.getMethods(), renderer, dnl())
			))
		);
	}
}