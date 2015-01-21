package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Field_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author Duncan
 */
public class JavaFieldView extends CodeView<Field_> {
	@Override
	public CharSequence render(CodeGenerator renderer, Field_ field) {
		return new $(
			renderer.on(field.getType_()),
			SPACE,
			lcfirst(field.getName_())
		);
	}
}