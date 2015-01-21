package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Class_;
import com.speedment.codegen.model.Package_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import static com.speedment.codegen.model.CodeModel.Type.*;
import static com.speedment.codegen.model.modifier.ClassModifier_.*;
import static com.speedment.codegen.CodeUtil.*;
import java.util.stream.Collectors;

/**
 *
 * @author Duncan
 */
public class JavaClassView extends CodeView<Class_> {
	private final static String 
		PACKAGE_STRING = "package ",
		PRIVATE_STRING = "private ",
		PROTECTED_STRING = "protected ",
		PUBLIC_STRING = "public ",
		FINAL_STRING = "final ",
		ABSTRACT_STRING = "abstract ",
		STATIC_STRING = "static ";
			
	@Override
	public CharSequence render(CodeGenerator renderer, Class_ clazz) {
		return new $(
			PACKAGE_STRING, 
			((Package_) renderer.last(PACKAGE)).getName_(), 
			SC, NL, NL,
			// TODO: Imports.
				
			// Accessability
			clazz.is(PUBLIC) ? PUBLIC_STRING : 
			clazz.is(PROTECTED) ? PROTECTED_STRING :
			clazz.is(PRIVATE) ? PRIVATE_STRING : EMPTY,
			
			// Finality
			clazz.is(FINAL) ? FINAL_STRING : EMPTY,
				
			// Abstract
			clazz.is(ABSTRACT) ? ABSTRACT_STRING : EMPTY,
			
			// Staticality
			clazz.is(STATIC) ? STATIC_STRING : EMPTY,
				
			// TODO: Class name, parent name, implemented interfaces, etc.
				
			// Block of content
			looseBracketsIndent(new $(
				
				// Render fields.
				clazz.getFields().stream()
					.map((f) -> renderer.on(f))
					.collect(Collectors.joining(NL)),
				NL, NL,
					
				// Render constructors.
				clazz.getConstructors().stream()
					.map((c) -> renderer.on(c))
					.collect(Collectors.joining(NL)),
				NL, NL,
					
				// Render methods.
				clazz.getMethods().stream()
					.map((m) -> renderer.on(m))
					.collect(Collectors.joining(NL))
			))
		);
	}
}