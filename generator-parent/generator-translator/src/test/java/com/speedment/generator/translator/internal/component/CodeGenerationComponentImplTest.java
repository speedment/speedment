/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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