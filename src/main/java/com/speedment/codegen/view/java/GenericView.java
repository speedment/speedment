package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.generic.Generic_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import java.util.Optional;

/**
 *
 * @author Duncan
 */
public class GenericView extends CodeView<Generic_> {
	private final static String EXTENDS_STRING = " extends ";

	@Override
	public Optional<CharSequence> render(CodeGenerator renderer, Generic_ model) {
		$ s = new $(model.getName());
		renderer.on(model.getExtendsType())
			.ifPresent(t -> s.$(new $(EXTENDS_STRING, t)));
		return Optional.of(s);
	}
	
}
