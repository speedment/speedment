/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.test_support;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.HasLabelSet;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.field.Field;

import java.util.stream.Stream;

public class MockStringManager implements Manager<String> {

    private final Persister<String> persister;
    private final Updater<String> updater;
    private final Remover<String> remover;

    public MockStringManager() {
        this.persister = new DummyPersister();
        this.updater = new DummyUpdater();
        this.remover = new DummyRemover();
    }

    @Override
    public Stream<String> stream() {
        return Stream.empty();
    }

    @Override
    public String create() {
        return "string";
    }

    @Override
    public TableIdentifier<String> getTableIdentifier() {
        return TableIdentifier.of("dbms", "schema", "table");
    }

    @Override
    public Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    public Stream<Field<String>> fields() {
        return Stream.empty();
    }

    @Override
    public Stream<Field<String>> primaryKeyFields() {
        return Stream.empty();
    }

    @Override
    public Persister<String> persister() {
        return persister;
    }

    @Override
    public Persister<String> persister(HasLabelSet<String> fields) {
        return persister;
    }

    @Override
    public Updater<String> updater() {
        return updater;
    }

    @Override
    public Updater<String> updater(HasLabelSet<String> fields) {
        return updater;
    }

    @Override
    public Remover<String> remover() {
        return remover;
    }

    private static final class DummyPersister implements Persister<String> {
        @Override
        public String apply(String s) {
            return s;
        }
    }

    private static final class DummyUpdater implements Updater<String> {
        @Override
        public String apply(String s) {
            return s;
        }
    }

    private static final class DummyRemover implements Remover<String> {
        @Override
        public String apply(String s) {
            return s;
        }
    }
}
