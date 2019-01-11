package com.speedment.runtime.core.manager;

public interface PersistenceProvider<ENTITY> {
    /**
     * Returns a {@link Persister} which can be used to persist
     * entities in the underlying database.
     * @see Persister#apply
     *
     * @return a Persister
     */
    Persister<ENTITY> persister();

    /**
     * Provides a {@link Persister} that operates on a given subset of entity fields. Useful
     * for example when persisting an entity with auto incremented fields of fields with
     * defaults.
     * See {@link #persister()}
     * @param fields the fields to persist, any others are ignored
     * @return a Persister
     */
    Persister<ENTITY> persister(HasLabelSet<ENTITY> fields);

    /**
     * Returns a {@link Updater} which can be used to update
     * entities in the underlying database.
     * @see Updater#apply
     *
     * @return an Updater
     */
    Updater<ENTITY> updater();

    /**
     * Provides a {@link Updater} that operates on a given subset of entity fields. Useful
     * for example when persisting an entity with auto incremented fields of fields with
     * defaults.
     * See {@link #updater()}
     * @param fields the fields to updatepersist, any others are ignored
     * @return a Persister
     */
    Updater<ENTITY> updater(HasLabelSet<ENTITY> fields);

    /**
     * Returns a {@link Remover} which can be used to remove
     * entities in the underlying database.
     * @see Remover#apply
     *
     * @return a Remover
     */
    Remover<ENTITY> remover();
}