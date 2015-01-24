package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.generic.Generic_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author Duncan
 */
public class GenericView extends CodeView<Generic_> {
	private final static String EXTENDS_STRING = " extends ";

	@Override
	public CharSequence render(CodeGenerator renderer, Generic_ model) {
		return new $(
			model.getName(), 
			model.getExtendsType() == null ? EMPTY : 
				new $(EXTENDS_STRING, renderer.on(model.getExtendsType()))
		);
	}
	
}
