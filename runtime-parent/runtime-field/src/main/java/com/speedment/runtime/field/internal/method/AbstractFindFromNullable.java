package com.speedment.runtime.field.internal.method;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.FindFromNullable;
import com.speedment.runtime.field.trait.HasComparableOperators;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base implementation of the {@link FindFromNullable}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
abstract class AbstractFindFromNullable<
    ENTITY,
    FK_ENTITY,
    V extends Comparable<? super V>,
    SOURCE extends Field<ENTITY>,
    TARGET extends Field<FK_ENTITY> & HasComparableOperators<FK_ENTITY, V>
> implements FindFromNullable<ENTITY, FK_ENTITY> {

    private final SOURCE source;
    private final TARGET target;
    private final TableIdentifier<FK_ENTITY> identifier;
    private final Supplier<Stream<FK_ENTITY>> streamSupplier;

    AbstractFindFromNullable(
        SOURCE source,
        TARGET target,
        TableIdentifier<FK_ENTITY> identifier,
        Supplier<Stream<FK_ENTITY>> streamSupplier) {

        this.source         = requireNonNull(source);
        this.target         = requireNonNull(target);
        this.identifier     = requireNonNull(identifier);
        this.streamSupplier = requireNonNull(streamSupplier);
    }

    @Override
    public final SOURCE getSourceField() {
        return source;
    }

    @Override
    public final TARGET getTargetField() {
        return target;
    }

    @Override
    public final TableIdentifier<FK_ENTITY> getTableIdentifier() {
        return identifier;
    }

    protected final Stream<FK_ENTITY> stream() {
        return streamSupplier.get();
    }
}
