/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.example.basic_example;

import com.company.sakila.SakilaApplication;
import com.company.sakila.db0.sakila.language.Language;
import com.company.sakila.db0.sakila.language.LanguageImpl;
import com.company.sakila.db0.sakila.language.LanguageManager;
import com.speedment.example.basic_example.util.ExampleUtil;
import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import com.speedment.runtime.core.ApplicationBuilder.LogType;

/**
 *
 * @author Per Minborg
 */
public class Crud {

    private final SakilaApplication app;
    private final LanguageManager languages;

    public Crud() {
        app = buildApplication(
            b -> b.withLogging(LogType.PERSIST),
            b -> b.withLogging(LogType.UPDATE),
            b -> b.withLogging(LogType.REMOVE)
        );
        languages = app.getOrThrow(LanguageManager.class);
    }

    public static void main(String[] args) {
        new Crud().run();
    }

    private void run() {
        printAllLanguages();
        create();
        printAllLanguages();
        update();
        printAllLanguages();
        remove();
        printAllLanguages();
    }

    private void create() {
        ExampleUtil.log("create");

        Language german = languages.persist(
            new LanguageImpl().setName("Deutsch")
        );

        System.out.format(
            "Language %s was persisted with id %d %n",
            german.getName(),
            german.getLanguageId()
        );

    }

    private void update() {
        ExampleUtil.log("update");

        languages.stream()
            .filter(Language.NAME.equal("Deutsch"))
            .map(Language.NAME.setTo("German"))
            .forEach(languages.updater());

    }

    private void remove() {
        ExampleUtil.log("remove");

        languages.stream()
            .filter(Language.NAME.equal("German"))
            .forEach(languages.remover());

    }

    private void printAllLanguages() {
        languages.stream()
            .forEachOrdered(System.out::println);
    }

}
