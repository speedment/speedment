package com.speedment.generator.standard;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.lifecycle.*;
import com.speedment.generator.translator.Translator;
import com.speedment.runtime.config.Project;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

final class ProjectTranslatorsTest {

    private final Injector injector = TestUtil.injector();

    @Test
    void speedmentJson() {
        test(Projects.SPEEDMENT_JSON);
    }

    @Test
    void spring() {
        test(Projects.SPRING_PLUGIN);
    }

    @Test
    void dataStore() {
        test(Projects.DATA_STORE);
    }

    @Test
    void foreignKey() {
        test(Projects.FOREIGN_KEY);
    }

    @Test
    void typeMapper() {
        test(Projects.TYPE_MAPPER);
    }

    void test(Projects projectHolder) {
        Stream.of(projectHolder)
            .map(Projects::project)
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
