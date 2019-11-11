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
import com.speedment.runtime.core.ApplicationBuilder.LogType;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;

/**
 *
 * @author Per Minborg
 */
public final class RestBackEnd {

    private static final String PG_13 = "PG-13";

    private final SakilaApplication app;
    private final FilmManager films;

    private RestBackEnd() {
        app = buildApplication(b -> b.withLogging(LogType.STREAM_OPTIMIZER));
        films = app.getOrThrow(FilmManager.class);
    }

    public static void main(String[] args) {
        new RestBackEnd().run();
    }

    private void run() {
        serveFilms(PG_13, 0).forEach(System.out::println);
        serveFilms(PG_13, 1).forEach(System.out::println);
        serveFilms(PG_13, 3).forEach(System.out::println);
    }

    private static final long PAGE_SIZE = 50;

    private Stream<Film> serveFilms(String rating, int page) {
        System.out.format("serveFilms(String rating=%s, int page=%d) %n", rating, page);

        Stream<Film> stream = films.stream();

        if (rating != null) {
            stream = stream.filter(Film.RATING.equal(rating));
        }

        return stream
            .sorted(Film.LENGTH)
            .skip(page * PAGE_SIZE)
            .limit(PAGE_SIZE);

    }

    private Stream<Film> serveFilmsVariant(String rating, int page) {
        System.out.format("serveFilmsVariant(String rating=%s, int page=%d) %n", rating, page);

        Stream<Film> stream = films.stream()
            .sorted(Film.LENGTH.reversed())
            .sorted(Film.RELEASE_YEAR)
            .filter(Film.LENGTH.greaterThan(2));

        if (rating != null) {
            stream = stream.filter(Film.RATING.equal(rating));
        }

        return stream
            .skip(page * PAGE_SIZE)
            .limit(PAGE_SIZE);

    }

    private Stream<Film> serveFilmsRatingNonNull(String rating, int page) {
        return films.stream()
            .filter(Film.RATING.equal(rating))
            .sorted(Film.LENGTH)
            .skip(page * PAGE_SIZE)
            .limit(PAGE_SIZE);
    }


    private Stream<Film> serveFilmsWithfunctionalFold(String rating, int page) {
        System.out.format("serveFilmsWithfunctionalFold(String rating=%s, int page=%d) %n", rating, page);

        return Stream.<UnaryOperator<Stream<Film>>>of(
            (s) -> rating == null ? s : s.filter(Film.RATING.equal(rating)),
            s -> s.sorted(Film.LENGTH),
            s -> s.skip(page * PAGE_SIZE),
            s -> s.limit(PAGE_SIZE)
        ).reduce(
            films.stream(),
            (s, o) -> o.apply(s),
            (a, b) -> a
        );

    }

}
