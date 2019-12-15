package com.speedment.generator.standard.entity;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.ProjectHolder;
import com.speedment.generator.standard.StandardTranslatorBundle;
import com.speedment.generator.translator.TranslatorBundle;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.provider.DelegateInfoComponent;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

final class EntityTranslatorTest {


    @Test
    void makeCodeGenModel() throws InstantiationException {
        final Injector injector = Injector.builder()
/*            .withComponent(StandardJavaGenerator.class)*/
            .withComponent(DelegateInfoComponent.class)
            .withBundle(TranslatorBundle.class)
            .withBundle(StandardTranslatorBundle.class)
            .build();

        Table table = DocumentDbUtil.traverseOver(ProjectHolder.SPEEDMENT_JSON.project(), Table.class).findFirst().orElseThrow(NoSuchElementException::new);

        EntityTranslator translator = new EntityTranslator(injector,table);

        File file = translator.get();

        System.out.println("file = " + file);

        Generator generator = injector.get(Generator.class).orElseThrow(NoSuchElementException::new);

        final String code = generator.on(file).orElseThrow(NoSuchElementException::new);

        System.out.println(code);


    }

    @Test
    void getClassOrInterfaceName() {
    }

    @Test
    void getJavadocRepresentText() {
    }
}