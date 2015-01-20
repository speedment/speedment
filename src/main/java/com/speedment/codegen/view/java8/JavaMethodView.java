package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Method_;
import com.speedment.codegen.view.CodeView;
import static com.speedment.codegen.CodeUtil.*;
import java.util.stream.Collectors;

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
		return $(
			method.isPublic_() ? PUBLIC :
			method.isProtected_() ? PROTECTED :
			method.isPrivate_() ? PRIVATE : "", 
			method.isFinal_() ? FINAL : "",
			method.isStatic_() ? STATIC : "",
			method.getName_(), PS,
				method.getParameters().stream()
					.map((param) -> renderer.on(param))
					.collect(Collectors.joining(COMMA)),
			PE, renderer.on(method.getBlock())
		);
	}
}