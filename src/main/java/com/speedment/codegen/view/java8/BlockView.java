package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Block_;
import com.speedment.codegen.view.CodeView;

import static com.speedment.codegen.CodeUtil.*;
import java.util.stream.Collectors;

/**
 *
 * @author Duncan
 */
public class BlockView extends CodeView<Block_> {
	@Override
	public CharSequence render(CodeGenerator renderer, Block_ block) {
		return looseBracketsIndent(
			block.getStatements().stream()
				.map((statement) -> renderer.on(statement))
				.collect(Collectors.joining(nl()))
		);
	}
}