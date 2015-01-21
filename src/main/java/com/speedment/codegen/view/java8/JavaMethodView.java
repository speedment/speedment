package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Method_;
import com.speedment.codegen.view.CodeView;
import static com.speedment.codegen.CodeUtil.*;
import java.util.stream.Collectors;
import com.speedment.util.$;

/**
 *
 * @author Duncan
 */
public class JavaMethodView extends CodeView<Method_> {
	private final static CharSequence 
		PUBLIC = "public ",
		PROTECTED = "protected ",
		PRIVATE = "private ",
		FINAL = "final ",
		STATIC = "static ",
		COMMA = ", ";
	
	@Override
	public CharSequence render(CodeGenerator renderer, Method_ method) {
		return new $(
			method.isPublic_() ? PUBLIC :
			method.isProtected_() ? PROTECTED :
			method.isPrivate_() ? PRIVATE : EMPTY, 
			method.isFinal_() ? FINAL : EMPTY,
			method.isStatic_() ? STATIC : EMPTY,
			method.getName_(), PS,
				method.getParameters().stream()
					.map((param) -> renderer.on(param))
					.collect(Collectors.joining(COMMA)),
			PE, renderer.on(method.getBlock_())
		);
	}
}