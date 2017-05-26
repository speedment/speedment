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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Per Minborg
 */
public class Classifying {

    private final SakilaApplication app;
    private final FilmManager films;

    public Classifying() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
    }

    public static void main(String[] args) {
        new Classifying().run();
    }

    private void run() {
        classifyFilms();
    }

    private void classifyFilms() {
        ExampleUtil.log("classifyFilms");

        Map<String, List<Film>> map = films.stream()
            .collect(
                Collectors.groupingBy(
                    // Apply this classifier
                    f -> f.getRating().orElse("none")
                )
            );

        map.forEach((k, v) -> 
            System.out.format(
                "Rating %-5s maps to %d films %n", k, v.size()
            )
        );

    }

}
