package com.speedment.generator.standard;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.entity.EntityImplTranslator;
import com.speedment.generator.standard.entity.EntityTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityImplTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityTranslator;
import com.speedment.generator.standard.lifecycle.*;
import com.speedment.generator.standard.manager.*;
import com.speedment.generator.translator.Translator;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.util.DocumentDbUtil;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

final class ProjectTranslatorsTest {

    private final Injector injector = TestUtil.injector();

    @Test
    void speedmentJson() {
        test(ProjectHolder.SPEEDMENT_JSON);
    }

    @Test
    void spring() {
        test(ProjectHolder.SPRING_PLUGIN);
    }

    void test(ProjectHolder projectHolder) {
        Stream.of(projectHolder)
            .map(ProjectHolder::project)
            .flatMap(this::translators)
            .forEach(this::test);
    }

    void test(Translator<Project, ?> translator) {
        assertNotNull(translator.getDocument());

        assertFalse(translator.column().isPresent());
        assertTrue(translator.project().isPresent());
        assertFalse(translator.dbms().isPresent());
        assertFalse(translator.schema().isPresent());

        final Generator generator = injector.get(Generator.class).orElseThrow(NoSuchElementException::new);
        final File file = translator.get();
        final String code = generator.on(file).orElseThrow(NoSuchElementException::new);

        assertTrue(code.contains("package"));
        assertTrue(code.contains("{"));
        assertTrue(code.contains("}"));
    }


    final  Stream<Translator<Project, ?>> translators(Project project) {
        return Stream.<BiFunction<Injector, Project, Translator<Project, ?>>>of(
            // entity
            ApplicationBuilderTranslator::new,
            ApplicationImplTranslator::new,
            ApplicationTranslator::new,
            GeneratedApplicationBuilderTranslator::new,
            GeneratedApplicationImplTranslator::new,
            GeneratedApplicationTranslator::new,
            GeneratedMetadataTranslator::new,
            InjectorProxyTranslator::new
        )
            .map(c -> c.apply(injector, project));
    }


}
