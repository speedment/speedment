package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.javadoc.Javadoc_;
import com.speedment.codegen.view.CodeView;
import java.util.Optional;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;
import com.speedment.util.CodeCombiner;

/**
 *
 * @author Emil Forslund
 */
public class JavadocView extends CodeView<Javadoc_> {

	@Override
	public Optional render(CodeGenerator renderer, Javadoc_ model) {
		return Optional.of(new $(
			model.getDescriptions().stream().collect(
				CodeCombiner.joinIfNotEmpty(
					new $(nl(), SPACE, STAR, SPACE), 
					new $(JAVADOC_START, nl(), SPACE, STAR, SPACE),
					new $(nl(), JAVADOC_END, nl())
				)
			)
		));
	}
	
}
