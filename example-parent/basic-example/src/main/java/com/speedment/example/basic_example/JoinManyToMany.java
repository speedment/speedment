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
import com.speedment.example.basic_example.util.ExampleUtil;
import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public class JoinManyToMany {

    private final SakilaApplication app;
    private final FilmManager films;
    private final ActorManager actors;
    private final FilmActorManager filmActors;

    public JoinManyToMany() {
        app = buildApplication();
        films = app.getOrThrow(FilmManager.class);
        actors = app.getOrThrow(ActorManager.class);
        filmActors = app.getOrThrow(FilmActorManager.class);

    }

    public static void main(String[] args) {
        new JoinManyToMany().run();
    }

    private void run() {
        manyToMany();
    }

    private void manyToMany() {
        ExampleUtil.log("manyToMany");

        Map<Actor, List<Film>> filmographies = filmActors.stream()
            .collect(
                groupingBy(actors.finderBy(FilmActor.ACTOR_ID), // Applies the FilmActor to ACTOR classifier
                    mapping(
                        films.finderBy(FilmActor.FILM_ID), // Applies the FilmActor to Film finder
                        toList()                           // Use a List collector for downstream aggregation.
                    )
                )
            );

        
        
        filmographies.forEach((a, fl) -> {
            System.out.format("%s -> %s %n",
                a.getFirstName() + " " + a.getLastName(),
                fl.stream().map(Film::getTitle).sorted().collect(toList())
            );
        });

    }

}
