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
import com.company.sakila.db0.sakila.actor.Actor;
import com.company.sakila.db0.sakila.actor.ActorManager;
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.company.sakila.db0.sakila.film_actor.FilmActor;
import com.company.sakila.db0.sakila.film_actor.FilmActorManager;
import com.company.sakila.db0.sakila.language.Language;
import com.company.sakila.db0.sakila.language.LanguageManager;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.example.basic_example.util.ExampleUtil;
import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public class JoinExamples {

    private final SakilaApplication app;
    private final FilmManager films;
    private final LanguageManager languages;
    private final ActorManager actors;
    private final JoinComponent joinComponent;

    public JoinExamples() {
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
        joinInMap();
        joinInMapEntity();
        oneToMany();
        manyToOne();
        manyToMany();
        manyToManySkipLinkTable();
        join();
        linked();
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
            -> System.out.format("%s: %s%n", l.getName(), fl.stream().map(Film::getTitle).collect(joining(", ")))
        );

    }

    private void oneToMany() {
        ExampleUtil.log("oneToMany");

        Join<Tuple2<Language, Film>> join = joinComponent
            .from(LanguageManager.IDENTIFIER)
            .innerJoinOn(Film.LANGUAGE_ID).equal(Language.LANGUAGE_ID)
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

}
