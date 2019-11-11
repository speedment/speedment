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
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.company.sakila.db0.sakila.film_actor.FilmActor;
import com.company.sakila.db0.sakila.film_actor.FilmActorManager;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.example.basic_example.util.ExampleUtil;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent;

import java.util.Map;
import java.util.NoSuchElementException;

import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 *
 * @author Per Minborg
 */
public final class Pivot {

    private final SakilaApplication app;
    private final FilmManager films;
    private final ActorManager actors;
    private final FilmActorManager filmActors;
    private final JoinComponent joinComponent;

    private Pivot() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
        actors = app.getOrThrow(ActorManager.class);
        filmActors = app.getOrThrow(FilmActorManager.class);
        joinComponent = app.getOrThrow(JoinComponent.class);
    }

    public static void main(String[] args) {
        new Pivot().run();
    }

    private void run() {
        pivot();
        pivotCustom();
    }

    private void pivot() {
        ExampleUtil.log("pivot");

        Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(Tuples::of);

        Map<Actor, Map<String, Long>> pivot = join.stream()
            .collect(
                groupingBy(
                    Tuple3::get2, // Applies Actor as classifier
                    groupingBy(
                        tu -> tu.get1().getRating().get(), // Applies rating as second level classifier
                        counting() // Counts the elements 
                    )
                )
            );

        pivot.forEach((k, v) -> {                            // keys: Actor, values: Map<String, Long>
            System.out.format("%22s  %5s %n", k.getFirstName() + " " + k.getLastName(), v);
        });

    }

    private void pivotCustom() {
        ExampleUtil.log("pivotCustom");

        Join<ActorRating> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(ActorRating::new);

        Map<Actor, Map<String, Long>> pivot = join.stream()
            .collect(
                groupingBy(
                    ActorRating::actor, // Applies Actor as classifier
                    groupingBy(
                        ActorRating::rating, // Applies rating as second level classifier
                        counting() // Counts the elements
                        )
                    )
                );

        pivot.forEach((k, v) -> {                            // keys: Actor, values: Map<String, Long>
            System.out.format("%22s  %5s %n", k.getFirstName() + " " + k.getLastName(), v);
        });

    }


    private static class ActorRating {
        private final Actor actor;
        private final String rating;

        private ActorRating(FilmActor fa, Film film, Actor actor) {
            // fa is not used
            this.actor = actor;
            this.rating = film.getRating().orElseThrow(NoSuchElementException::new);
        }

        public Actor actor() {
            return actor;
        }

        public String rating() {
            return rating;
        }

    }


}
