package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.modifier.Modifier_;
import com.speedment.codegen.view.CodeView;
import java.util.Optional;

/**
 *
 * @author Duncan
 */
public class ModifierView extends CodeView<Modifier_> {
	@Override
	public Optional<CharSequence> render(CodeGenerator renderer, Modifier_ model) {
		return Optional.of(model.name());
	}
}
