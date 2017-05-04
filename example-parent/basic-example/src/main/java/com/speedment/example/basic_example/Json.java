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
import com.company.sakila.db0.sakila.actor.ActorManager;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.company.sakila.db0.sakila.film_actor.FilmActorManager;
import static com.speedment.oracle_java_magazine.util.ExampleUtil.buildApplication;
//import com.speedment.plugins.json.JsonBundle;

/**
 *
 * @author Per Minborg
 */
public class Json {

    private final SakilaApplication app;
    private final FilmManager films;
    private final FilmActorManager filmActors;
    private final ActorManager actors;

    public Json() {
        app = buildApplication(/*b -> b.withBundle(JsonBundle.class)*/);
        films = app.getOrThrow(FilmManager.class);
        filmActors = app.getOrThrow(FilmActorManager.class);
        actors = app.getOrThrow(ActorManager.class);
    }

    public static void main(String[] args) {
        new Json().run();
    }

    private void run() {
//        json();
    }

//    private void json() {
//        ExampleUtil.log("json");
//
//        final JsonComponent jsonComponent = app.getOrThrow(JsonComponent.class);
//
//        Function<Film, Stream<Actor>> mapper = f -> filmActors.findBackwardsBy(FilmActor.FILM_ID, f).map(actors.finderBy(FilmActor.ACTOR_ID));
//
//        String json = films.stream()
//            .collect(JsonCollector.toJson(
//                jsonComponent.allOf(films)
//                    .remove(Film.FILM_ID)
//                    .remove(Film.DESCRIPTION)
//                    .putStreamer(
//                        "actors", // Declare a new attribute
//                        mapper, // How it is calculated
//                        jsonComponent.noneOf(actors) // How it is formatted
//                            .put(Actor.FIRST_NAME)
//                    )
//            ));
//        
//        
//        System.out.println(json);
//
//    }

}
