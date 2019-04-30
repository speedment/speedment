/**
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
package com.speedment.example.basic_example.extra;

import com.company.sakila.SakilaApplication;
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.company.sakila.db0.sakila.language.Language;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent;

import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;

/**
 *
 * @author Per Minborg
 */
public class JoinGenericTables {

    private final SakilaApplication app;
    private final JoinComponent joinComponent;
    private final FilmManager films;

    private JoinGenericTables() {
        app = buildApplication();
        joinComponent = app.getOrThrow(JoinComponent.class);
        films = app.getOrThrow(FilmManager.class);
    }

    public static void main(String[] args) {
        new JoinGenericTables().run();
    }

    private void run() {
        joinGenericTables(
            FilmManager.IDENTIFIER,
            Film.LANGUAGE_ID,
            Language.LANGUAGE_ID
        );
    }

    private <T0, T1> void joinGenericTables(
        TableIdentifier<T0> ti0,
        HasComparableOperators<T0, ?> f0,
        HasComparableOperators<T1, ?> f1
    ) {

        Join<Tuple2<T0, T1>> join = joinComponent.from(ti0)
            .innerJoinOn(f1).equal(f0)
            .build(Tuples::of);

        join.stream()
            .limit(3)
            .forEachOrdered(System.out::println);


    }

}
