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
package com.speedment.plugins.json.datamodel;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.*;
import com.speedment.runtime.field.Field;

import java.util.stream.Stream;

public final class UserManager implements Manager<User> {

    @Override
    public Stream<User> stream() {
        return Stream.empty();
    }

    @Override
    public User create() {
        return new UserImpl();
    }

    @Override
    public TableIdentifier<User> getTableIdentifier() {
        return TableIdentifier.of("speedment_test","speedment_test", "unsigned_test");
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Stream<Field<User>> fields() {
        return Stream.of(User.ID, User.NAME);
    }

    @Override
    public Stream<Field<User>> primaryKeyFields() {
        return Stream.of(User.ID);
    }

    @Override public Persister<User> persister() {throw new UnsupportedOperationException(); }
    @Override public Persister<User> persister(HasLabelSet<User> fields) { throw new UnsupportedOperationException(); }
    @Override public Updater<User> updater() { throw new UnsupportedOperationException(); }
    @Override public Updater<User> updater(HasLabelSet<User> fields) { throw new UnsupportedOperationException(); }
    @Override public Remover<User> remover() { throw new UnsupportedOperationException(); }
}
