package com.speedment.plugins.json;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.*;
import com.speedment.runtime.field.Field;

import java.util.stream.Stream;

public final class LanguageManager implements Manager<Language> {

    @Override
    public Stream<Language> stream() {
        return Stream.empty();
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
