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

public final class LanguageManager implements Manager<Language> {

    @Override
    public Stream<Language> stream() {
        return Stream.of(
                new LanguageImpl()
                        .setId(1)
                        .setName("English"),
                new LanguageImpl()
                        .setId(2)
                        .setName("German"),
                new LanguageImpl()
                        .setId(3)
                        .setName("Swedish")
        );
    }

    @Override
    public Language create() {
        return new LanguageImpl();
    }

    @Override
    public TableIdentifier<Language> getTableIdentifier() {
        return TableIdentifier.of("speedment_test","speedment_test", "unsigned_test");
    }

    @Override
    public Class<Language> getEntityClass() {
        return Language.class;
    }

    @Override
    public Stream<Field<Language>> fields() {
        return Stream.of(Language.ID, Language.NAME);
    }

    @Override
    public Stream<Field<Language>> primaryKeyFields() {
        return Stream.of(Language.ID);
    }

    @Override public Persister<Language> persister() {throw new UnsupportedOperationException(); }
    @Override public Persister<Language> persister(HasLabelSet<Language> fields) { throw new UnsupportedOperationException(); }
    @Override public Updater<Language> updater() { throw new UnsupportedOperationException(); }
    @Override public Updater<Language> updater(HasLabelSet<Language> fields) { throw new UnsupportedOperationException(); }
    @Override public Remover<Language> remover() { throw new UnsupportedOperationException(); }
}
