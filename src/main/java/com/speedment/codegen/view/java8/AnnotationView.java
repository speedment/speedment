package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Annotation_;
import com.speedment.codegen.view.CodeView;
import static com.speedment.codegen.CodeUtil.*;
import com.speedment.util.$;

/**
 *
 * @author Duncan
 */
public class AnnotationView extends CodeView<Annotation_> {
	@Override
	public CharSequence render(CodeGenerator renderer, Annotation_ model) {
		return new $(AT, model.getAnnotationClass().getName());
	}
}
