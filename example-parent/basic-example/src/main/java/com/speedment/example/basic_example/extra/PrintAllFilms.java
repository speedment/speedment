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
package com.speedment.example.basic_example.extra;

import com.company.sakila.SakilaApplication;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.speedment.example.basic_example.util.ExampleUtil;
import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;

/**
 *
 * @author Per Minborg
 */
public class PrintAllFilms {

    private final SakilaApplication app;
    private final FilmManager films;

    public PrintAllFilms() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
    }

    public static void main(String[] args) {
        new PrintAllFilms().run();
    }

    private void run() {
        printAllFilms();
    }

    private void printAllFilms() {
        ExampleUtil.log("printAllFilms");

        films.stream()
            .forEach(System.out::println);

    }

}
