package com.speedment.orm.code.model.java;

import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.java.JavaGenerator;
import com.speedment.codegen.java.JavaInstaller;
import com.speedment.orm.code.model.Translator;
import com.speedment.orm.code.model.java.entity.EntityTranslator;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class MainGenerator implements Consumer<Project> {

    @Override
    public void accept(Project project) {
        final List<Translator<?, ?>> translators = new ArrayList<>();

        project.traversalOf(Table.class).forEach(table -> {
            translators.add(new EntityTranslator(table));
        });

        final CodeGenerator cg = new JavaGenerator(
                new JavaInstaller()
        );

        translators.forEach(t -> {
            final Optional<String> code = cg.on(t.get());
            System.out.println(code);
        });

    }

}
