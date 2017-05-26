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
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.speedment.example.basic_example.util.ExampleUtil;
import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public class Optimization {

    private final SakilaApplication app;
    private final FilmManager films;

    public Optimization() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
    }

    public static void main(String[] args) {
        new Optimization().run();
    }

    private void run() {
        findALongFilm();
        countPg13Films();
        understandingOptimization();
    }

    private void findALongFilm() {
        ExampleUtil.log("findALongFilm");

        Optional<Film> longFilm = films.stream()
            .filter(Film.LENGTH.greaterThan(120))
            .findAny();

        longFilm.ifPresent(System.out::println);

    }

    private void countPg13Films() {
        ExampleUtil.log("countPg13Films");

        long count = films.stream()
            .filter(Film.RATING.equal("PG-13"))
            .count();

        System.out.format(
            "There are %d films rated 'PG-13'%n", count
        );

    }

    private void understandingOptimization() {
        ExampleUtil.log("understandingOptimization");

        long count = films.stream()
            .filter(Film.RATING.equal("PG-13"))
            .filter(Film.LENGTH.greaterThan(75))
            .map(Film::getTitle)
            .sorted()
            .count();

        System.out.format("Found %d films%n", count);

    }

}
