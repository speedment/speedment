package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.view.CodeView;

/**
 *
 * @author Duncan
 */
public class OperatorView extends CodeView<Operator_> {

	@Override
	public CharSequence render(CodeGenerator renderer, Operator_ op) {
		return op.name();
	}
	
}
