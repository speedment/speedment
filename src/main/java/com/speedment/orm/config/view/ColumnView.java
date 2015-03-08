package com.speedment.orm.config.view;

import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.base.CodeView;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.impl.utils.MethodsParser;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 */
public class ColumnView implements CodeView<Column> {

	@Override
	public Optional<String> render(CodeGenerator cg, Column model) {
		return Optional.of("column " + block(
            MethodsParser.streamOfExternal(Column.class)
                .map(m -> m.getName() + " = " + "\"" + "\"")
                .collect(Collectors.joining(nl())),
		));
	}
	
}
