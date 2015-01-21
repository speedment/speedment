package com.speedment.codegen.view;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.CodeModel;

/**
 *
 * @author Duncan
 * @param <Model>
 */
public abstract class CodeView<Model extends CodeModel> {
	public abstract CharSequence render(final CodeGenerator renderer, final Model model);
}