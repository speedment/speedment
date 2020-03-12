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

package com.speedment.runtime.core.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.core.exception.SpeedmentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Predicate;

final class SqlPredicateTest {

    @ParameterizedTest
    @ValueSource(strings = {"test", ""})
    void wrap(String input) throws SQLException {
        Assertions.assertThrows(NullPointerException.class, () -> SqlPredicate.wrap(null));

        final Predicate<String> predicate = string -> string != null && !string.isEmpty();
        final SqlPredicate<String> sqlPredicate = SqlPredicate.wrap(predicate);

        assertNotNull(sqlPredicate);
        assertEquals(predicate.test(input), sqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", ""})
    void unWrap(String input) throws SQLException {
        final SqlPredicate<String> sqlPredicate = string -> string != null && !string.isEmpty();
        final Predicate<String> predicate = sqlPredicate.unWrap();

        assertNotNull(predicate);
        assertEquals(sqlPredicate.test(input), predicate.test(input));

        final SqlPredicate<String> throwingSqlPredicate = string -> {
            throw new SQLException();
        };
        final Predicate<String> throwingPredicate = throwingSqlPredicate.unWrap();

        assertThrows(SpeedmentException.class, () -> throwingPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test"})
    void and(String input) throws SQLException {
        final SqlPredicate<String> first = Objects::nonNull;
        final SqlPredicate<String> second = string -> !string.isEmpty();

        assertThrows(NullPointerException.class, () -> first.and(null));

        final SqlPredicate<String> sqlPredicate = first.and(second);

        assertTrue(sqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void and2(String input) throws SQLException {
        final SqlPredicate<String> first = Objects::nonNull;
        final SqlPredicate<String> second = string -> !string.isEmpty();

        assertThrows(NullPointerException.class, () -> first.and(null));

        final SqlPredicate<String> sqlPredicate = first.and(second);

        assertFalse(sqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void and3(String input) throws SQLException {
        final SqlPredicate<String> first = Objects::isNull  ;
        final SqlPredicate<String> second = string -> !string.isEmpty();

        assertThrows(NullPointerException.class, () -> first.and(null));

        final SqlPredicate<String> sqlPredicate = first.and(second);

        assertFalse(sqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test"})
    void negate(String input) throws SQLException {
        final SqlPredicate<String> sqlPredicate = string -> string != null && !string.isEmpty();

        assertTrue(sqlPredicate.test(input));

        final SqlPredicate<String> negatedSqlPredicate = sqlPredicate.negate();

        assertFalse(negatedSqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void negate2(String input) throws SQLException {
        final SqlPredicate<String> sqlPredicate = string -> string != null && !string.isEmpty();

        assertFalse(sqlPredicate.test(input));

        final SqlPredicate<String> negatedSqlPredicate = sqlPredicate.negate();

        assertTrue(negatedSqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void or(String input) throws SQLException {
        final SqlPredicate<String> first = Objects::isNull;
        final Predicate<String> second = String::isEmpty;

        assertThrows(NullPointerException.class, () -> first.or(null));

        final SqlPredicate<String> sqlPredicate = first.or(second);

        assertTrue(sqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test"})
    void or2(String input) throws SQLException {
        final SqlPredicate<String> first = Objects::isNull;
        final Predicate<String> second = String::isEmpty;

        assertThrows(NullPointerException.class, () -> first.or(null));

        final SqlPredicate<String> sqlPredicate = first.or(second);

        assertFalse(sqlPredicate.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test"})
    void or3(String input) throws SQLException {
        final SqlPredicate<String> first = Objects::isNull;
        final Predicate<String> second = string -> !string.isEmpty();

        assertThrows(NullPointerException.class, () -> first.or(null));

        final SqlPredicate<String> sqlPredicate = first.or(second);

        assertTrue(sqlPredicate.test(input));
    }
}
