package com.speedment.codegen.view;

import com.speedment.codegen.model.CodeModel;

/**
 *
 * @author Duncan
 */
public interface CodeViewBuilder {
	public CodeView getView(CodeModel.Type type);
}