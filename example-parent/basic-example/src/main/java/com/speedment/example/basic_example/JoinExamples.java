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
import com.company.sakila.db0.sakila.actor.Actor;
import com.company.sakila.db0.sakila.actor.ActorManager;
import com.company.sakila.db0.sakila.city.City;
import com.company.sakila.db0.sakila.city.CityManager;
import com.company.sakila.db0.sakila.country.Country;
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.company.sakila.db0.sakila.film_actor.FilmActor;
import com.company.sakila.db0.sakila.film_actor.FilmActorManager;
import com.company.sakila.db0.sakila.language.Language;
import com.company.sakila.db0.sakila.language.LanguageManager;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.common.tuple.nullable.Tuple2OfNullables;
import com.speedment.example.basic_example.util.ExampleUtil;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent;

import java.util.*;
import java.util.function.Function;

import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import static java.util.stream.Collectors.*;

/**
 *
 * @author Per Minborg
 */
public final class JoinExamples {

    private final SakilaApplication app;
    private final FilmManager films;
    private final LanguageManager languages;
    private final ActorManager actors;
    private final JoinComponent joinComponent;

    private JoinExamples() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
        languages = app.getOrThrow(LanguageManager.class);
        actors = app.getOrThrow(ActorManager.class);
        joinComponent = app.getOrThrow(JoinComponent.class);
    }

    public static void main(String[] args) {
        new JoinExamples().run();
    }

    private void run() {

        crossJoin();
        crossJoinWithFiltering();
        leftJoin();
        joinInMap();
        joinInMapEntity();
        oneToMany();
        oneToManyDefaultConstructor();
        oneToManyCustomConstructor();
        manyToOne();
        manyToMany();
        manyToManySorted();
        manyToManySortedCombined();
        manyToManySkipLinkTable();
        join();
        linked();

        selfJoin();
        selfJoin2();

        joinGroupByOrderBy();

    }

    private void crossJoin() {
        ExampleUtil.log("crossJoin");

        Join<Tuple2<Film, Language>> join = joinComponent
            .from(FilmManager.IDENTIFIER)
            .crossJoin(LanguageManager.IDENTIFIER)
            .build(Tuples::of);

        join.stream()
            .forEach(System.out::println);

    }

    private void crossJoinWithFiltering() {
        ExampleUtil.log("crossJoinWithFiltering");

        Join<Tuple2<Film, Language>> join = joinComponent
            .from(FilmManager.IDENTIFIER)
            // Restrict films so that only PG-13 rated films appear            
            .where(Film.RATING.equal("PG-13"))
            .crossJoin(LanguageManager.IDENTIFIER)
            // Restrict languages so that only films where English is spoken appear
            .where(Language.NAME.equal("English"))
            .build(Tuples::of);

        join.stream()
            .forEach(System.out::println);

    }

    private void leftJoin() {
        ExampleUtil.log("leftJoin");

        Join<Tuple2OfNullables<City, Country>> join = joinComponent
            .from(CityManager.IDENTIFIER)
            .leftJoinOn(Country.COUNTRY_ID).equal(City.COUNTRY_ID).where(Country.COUNTRY.startsWith("A"))
            .build();

        join.stream()
            .forEach(System.out::println);

    }

    private void joinInMap() {
        ExampleUtil.log("joinInMap");

        Join<Tuple2<Film, Language>> join = joinComponent
            .from(FilmManager.IDENTIFIER)
            .innerJoinOn(Language.LANGUAGE_ID).equal(Film.LANGUAGE_ID)
            .build(Tuples::of);

        Map<Language, List<Tuple2<Film, Language>>> languageFilmMap = join.stream()
            .collect(
                // Apply this classifier
                groupingBy(Tuple2::get1)
            );

    }

    private void joinInMapEntity() {
        ExampleUtil.log("joinInMapEntity");

        Join<Tuple2<Film, Language>> join = joinComponent
            .from(FilmManager.IDENTIFIER)
            .innerJoinOn(Language.LANGUAGE_ID).equal(Film.LANGUAGE_ID)
            .build(Tuples::of);

        Map<Language, List<Film>> languageFilmMap2 = join.stream()
            .collect(
                // Apply this classifier
                groupingBy(Tuple2::get1,
                    // Map down-stream elements and collect to a list
                    mapping(Tuple2::get0, toList())
                )
            );

        languageFilmMap2.forEach((l, fl)
            -> System.out.format("%s: %s %n", l.getName(), fl.stream().map(Film::getTitle).collect(joining(", ")))
        );

    }

    private void oneToManyDefaultConstructor() {
        ExampleUtil.log("oneToManyDefaultConstructor");

        Join<Tuple2OfNullables<Language, Film>> join = joinComponent
            // Start with the Language table
            .from(LanguageManager.IDENTIFIER)
            // Join with the Film table where the column
            // 'film,language_id` is equal to the column
            // `language.language.id'.
            .innerJoinOn(Film.LANGUAGE_ID).equal(Language.LANGUAGE_ID)
            // Create elements in the stream using the JoinComponents 
            // default element constructor (that creates
            // Tuple2OfNullables<Language, Film>
            .build();

        // Use the Join object to create Tuples of matching entities
        join.stream()
            .forEach(System.out::println);

    }

    private void oneToManyCustomConstructor() {
        ExampleUtil.log("oneToManyCustomConstructor");

        Join<TitleLanguageName> join = joinComponent
            .from(LanguageManager.IDENTIFIER)
            .innerJoinOn(Film.LANGUAGE_ID).equal(Language.LANGUAGE_ID)
            // Use a custom constructor that takes a Language entity and
            // a Film entity as input.
            .build(TitleLanguageName::new);

        join.stream()
            .forEach(System.out::println);

    }

    private void oneToMany() {
        ExampleUtil.log("oneToMany");

        Join<Tuple2<Language, Film>> join = joinComponent
            .from(LanguageManager.IDENTIFIER)
            .innerJoinOn(Film.LANGUAGE_ID).equal(Language.LANGUAGE_ID)
            // Use a custom Tuple constructor that takes a Language and
            // Film as input.
            .build(Tuples::of);

        join.stream()
            .forEach(System.out::println);

    }

    private void manyToOne() {
        ExampleUtil.log("manyToOne");

        Join<Tuple2<Film, Language>> join = joinComponent
            .from(FilmManager.IDENTIFIER).where(Film.RATING.equal("PG-13"))
            .innerJoinOn(Language.LANGUAGE_ID).equal(Film.LANGUAGE_ID)
            .build(Tuples::of);

        join.stream()
            .forEach(System.out::println);

    }

    private void manyToMany() {
        ExampleUtil.log("manyToMany");

        Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(Tuples::of);

        join.stream()
            .forEach(System.out::println);

    }


    private void manyToManySorted() {
        ExampleUtil.log("manyToManySorted");

        Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(Tuples::of);

        Comparator<Tuple3<FilmActor, Film, Actor>> byLength =Film.LENGTH.asInt().compose(Tuple3.getter1());

        join.stream()
            .sorted(byLength.reversed())
            .limit(100)
            .forEach(System.out::println);

    }

    private void manyToManySortedCombined() {
        ExampleUtil.log("manyToManySortedCombined");

        Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(Tuples::of);

        // Explicitly declare the Comparator types
        Comparator<Tuple3<FilmActor, Film, Actor>> byLength = Film.LENGTH.asInt().compose(Tuple3.getter1());
        Comparator<Tuple3<FilmActor, Film, Actor>> byActorName = Actor.LAST_NAME.compose(Tuple3.getter2());

        join.stream()
            .sorted(byLength.reversed().thenComparing(byActorName))
            .limit(100)
            .forEach(System.out::println);

        /*join.stream()
            .sorted(Comparator.comparing(Tuple3.<FilmActor, Film, Actor>getter1().andThen(Film.LENGTH.getter())))
            .forEach(System.out::println);*/

    }


    private void manyToManySkipLinkTable() {
        ExampleUtil.log("manyToManySkipLinkTable");

        Join<Tuple2<Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build((fa, f, a) -> Tuples.of(f, a)); // Apply a custom constructor, discarding FilmActor

        join.stream()
            .forEach(System.out::println);

    }

    private void join() {
        ExampleUtil.log("join");

        Join<Tuple2<Film, Language>> join = joinComponent
            .from(FilmManager.IDENTIFIER)
            .innerJoinOn(Language.LANGUAGE_ID).equal(Film.LANGUAGE_ID)
            .build(Tuples::of);

        join.stream()
            .map(t2 -> String.format("The film '%s' is in %s", t2.get0().getTitle(), t2.get1().getName()))
            .forEach(System.out::println);

    }

    private void selfJoin() {
        ExampleUtil.log("selfJoin");

        Join<Tuple2<Actor, Actor>> join = joinComponent
            .from(ActorManager.IDENTIFIER)
            .innerJoinOn(Actor.FIRST_NAME.tableAlias("B")).equal(Actor.FIRST_NAME)
            .build(Tuples::of);

        join.stream()
            .forEach(System.out::println);

    }


    private void selfJoin2() {
        ExampleUtil.log("selfJoin2");

        Join<Tuple3<Actor, Actor, Actor>> join = joinComponent
            .from(ActorManager.IDENTIFIER)
            .innerJoinOn(Actor.FIRST_NAME.tableAlias("B")).equal(Actor.FIRST_NAME)
            .innerJoinOn(Actor.LAST_NAME.tableAlias("C")).equal(Actor.LAST_NAME.tableAlias("B"))
            .build(Tuples::of);

        join.stream()
            .forEach(System.out::println);

    }


    private void linked() {
        ExampleUtil.log("linked");

        Optional<Film> anyFilmInEnglish = languages.stream()
            .filter(Language.NAME.equal("english"))
            .flatMap(films.finderBackwardsBy(Film.LANGUAGE_ID))
            .findAny();

        Optional<Language> languageOfFilmWithId42 = films.stream()
            .filter(Film.FILM_ID.equal(42))
            .map(languages.finderBy(Film.LANGUAGE_ID))
            .findAny();

    }

    private void joinInMapOld() {
        ExampleUtil.log("joinInMap");

        Map<Language, List<Film>> languageFilmMap = films.stream()
            .collect(
                // Apply this foreign key classifier
                groupingBy(languages.finderBy(Film.LANGUAGE_ID))
            );

        languageFilmMap.forEach((l, fl)
            -> System.out.format("%s: %s%n", l.getName(), fl.stream().map(Film::getTitle).collect(joining(", ")))
        );

    }

    private void joinGroupByOrderBy() {
        ExampleUtil.log("joinGroupByOrderBy");

        Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(Tuples::of);

        Comparator<Tuple2<String, String>> comparator = Comparator.comparing((Function<Tuple2<String, String>, String>) Tuple2::get0).thenComparing(Tuple2::get1);

        Map<Tuple2<String, String>, Long> grouped = join.stream()
            .collect(
                groupingBy(t -> Tuples.of(t.get1().getRating().orElse("Unknown"), t.get2().getLastName()), () -> new TreeMap<>(comparator), counting())
            )
            ;

        grouped.forEach((k, v) -> {
            System.out.format("%-32s, %,d%n", k, v);
        });


    }


    private final class TitleLanguageName {

        private final String title;
        private final String languageName;

        private TitleLanguageName(Language language, Film film) {
            this.title = film.getTitle();
            this.languageName = language.getName();
        }

        public String title() {
            return title;
        }

        public String languageName() {
            return languageName;
        }

        @Override
        public String toString() {
            return "TitleLanguageName{" + "title=" + title + ", languageName=" + languageName + '}';
        }

    }

}
