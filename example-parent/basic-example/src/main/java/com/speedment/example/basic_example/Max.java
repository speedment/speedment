/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.speedment.example.basic_example.util.ExampleUtil;
import com.speedment.runtime.core.ApplicationBuilder;

import java.util.Optional;

import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;

/**
 *
 * @author Per Minborg
 */
public final class Max {

    private final SakilaApplication app;
    private final FilmManager films;

    private Max() {
        app = buildApplication(b -> b.withLogging(ApplicationBuilder.LogType.STREAM));
        films = app.getOrThrow(FilmManager.class);
    }

    public static void main(String[] args) {
        new Max().run();
    }

    private void run() {
        max();
        maxViaOrder();
    }

    private void max() {
        ExampleUtil.log("max");

        Optional<Film> longestFilm = films.stream()
            .max(Film.LENGTH);

        System.out.println(longestFilm);


    }

    private void maxViaOrder() {
        ExampleUtil.log("maxViaOrder");

        Optional<Film> longestFilm = films.stream()
            .sorted(Film.LENGTH.reversed())
            .limit(1)
            .findFirst();


        System.out.println(longestFilm);


    }



}
