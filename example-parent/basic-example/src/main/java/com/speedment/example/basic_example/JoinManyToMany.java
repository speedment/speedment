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

import java.util.List;
import java.util.Map;

import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import static java.util.stream.Collectors.*;

/**
 *
 * @author Per Minborg
 */
public final class JoinManyToMany {

    private final SakilaApplication app;
    private final FilmManager films;
    private final ActorManager actors;
    private final FilmActorManager filmActors;
    private final JoinComponent joinComponent;

    private JoinManyToMany() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
        actors = app.getOrThrow(ActorManager.class);
        filmActors = app.getOrThrow(FilmActorManager.class);
        joinComponent = app.getOrThrow(JoinComponent.class);
    }

    public static void main(String[] args) {
        new JoinManyToMany().run();
    }

    private void run() {
        manyToMany();
    }

    private void manyToMany() {
        ExampleUtil.log("manyToMany");

        Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(Tuples::of);

        
        Map<Actor, List<Film>> filmographies = join.stream()
            .collect(
                groupingBy(Tuple3::get2, // Applies Actor as classifier
                    mapping(
                        Tuple3::get1, // Extracts Film from the Tuple
                        toList() // Use a List collector for downstream aggregation.
                    )
                )
            );
        
//        
//        
//        Map<Actor, List<Film>> filmographies = filmActors.stream()
//            .collect(
//                groupingBy(actors.finderBy(FilmActor.ACTOR_ID), // Applies the FilmActor to ACTOR classifier
//                    mapping(
//                        films.finderBy(FilmActor.FILM_ID), // Applies the FilmActor to Film finder
//                        toList() // Use a List collector for downstream aggregation.
//                    )
//                )
//            );

        filmographies.forEach((a, fl) -> {
            System.out.format("%s -> %s %n",
                a.getFirstName() + " " + a.getLastName(),
                fl.stream().map(Film::getTitle).sorted().collect(toList())
            );
        });

    }

}
