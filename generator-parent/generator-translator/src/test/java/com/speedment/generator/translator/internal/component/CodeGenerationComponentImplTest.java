package com.speedment.generator.translator.internal.component;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeGenerationComponentImplTest {

   private CodeGenerationComponentImpl instance;
   private Injector injectorEmpty;

   @BeforeEach
   void setup() throws InstantiationException {
        instance = new CodeGenerationComponentImpl();
        injectorEmpty = Injector.builder().build();
   }

    @Test
    void setInjector() {
        instance.setInjector(injectorEmpty);
    }

    @Test
    void newClass() {
    }

    @Test
    void newEnum() {
    }

    @Test
    void newInterface() {
    }

    @Test
    void forEveryTable() {
    }

    @Test
    void forEverySchema() {
    }

    @Test
    void forEveryDbms() {
    }

    @Test
    void decorate() {
    }

    @Test
    void put() {
    }

    @Test
    void add() {
    }

    @Test
    void remove() {
    }

    @Test
    void translators() {
    }

    @Test
    void translatorKeys() {
    }

    @Test
    void findTranslator() {
    }
}