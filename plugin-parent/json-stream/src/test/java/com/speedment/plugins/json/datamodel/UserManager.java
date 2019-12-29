package com.speedment.plugins.json;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
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
