/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import com.speedment.example.basic_example.util.ExampleUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public class UsersGuide {

    private final SakilaApplication app;
    private final FilmManager films;

    public UsersGuide() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
    }

    public static void main(String[] args) {
        new UsersGuide().run();
    }

    private void run() {
        distinct();
        mapToInt();
        mapToTuple();
        findId();
    }

    private void distinct() {
        Set<String> ratings = films.stream()
                .map(Film.RATING)
                .distinct()
                .collect(Collectors.toSet());

        System.out.println(ratings);

    }

    private void mapToInt() {
        final IntStream ids = films.stream()
                .mapToInt(Film.FILM_ID);


        ids.forEach(System.out::println);

    }


    private void mapToTuple() {
        Stream<Tuple2<String, Integer>> items = films.stream()
                .map(Tuples.toTuple(Film.TITLE, Film.LENGTH.getter()));

        items.forEach(System.out::println);

    }

    private void findId() {
        int id = 100;

        final Optional<String> title = films.stream()
                .filter(Film.FILM_ID.equal(id))      // find the films we are looking for
                .map(Film.TITLE)                     // switch from a stream of films to one of titles
                .findAny();                          // we want the first and only match for this unique key

        if (title.isPresent()) {
            System.out.format("Film ID %d has title %s.", id, title.get());
        } else {
            System.out.format("Film ID not found.", id);
        }
    }

}
