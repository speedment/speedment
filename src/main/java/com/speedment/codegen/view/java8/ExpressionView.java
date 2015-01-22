package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Expression_;
import com.speedment.codegen.view.CodeView;

/**
 *
 * @author Duncan
 */
public class ExpressionView extends CodeView<Expression_> {

	@Override
	public CharSequence render(CodeGenerator renderer, Expression_ expression) {
		return expression.getStringExpression();
	}
}