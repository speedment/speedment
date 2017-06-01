package com.speedment.runtime.field.internal.method;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.method.FindFromNullable;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasReferenceOperators;
import com.speedment.runtime.field.trait.HasReferenceValue;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 * Default implementation of {@link FindFromNullable} for
 * {@link ReferenceField ReferenceFields}.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class FindFromNullableReference<
    ENTITY, FK_ENTITY,
    V extends Comparable<? super V>,
    SOURCE extends Field<ENTITY>
                 & HasReferenceOperators<ENTITY>
                 & HasReferenceValue<ENTITY, ?, V>,
    TARGET extends Field<FK_ENTITY>
                 & HasComparableOperators<FK_ENTITY, V>
> extends AbstractFindFromNullable<ENTITY, FK_ENTITY, V, SOURCE, TARGET>
implements FindFromNullable<ENTITY, FK_ENTITY> {

    public FindFromNullableReference(
            SOURCE source,
            TARGET target,
            TableIdentifier<FK_ENTITY> foreignTable,
            Supplier<Stream<FK_ENTITY>> streamSupplier) {

        super(source, target, foreignTable, streamSupplier);
    }

    @Override
    public boolean isPresent(ENTITY entity) {
        return getSourceField().isNotNull().test(entity);
    }

    @Override
    public FK_ENTITY applyOrThrow(ENTITY entity)
            throws IllegalArgumentException {

        return apply(entity).findAny()
            .orElseThrow(() -> new IllegalArgumentException(format(
                "Specified entity '%s' does not reference any %s.",
                entity, getTableIdentifier()
            )));
    }

    @Override
    public Stream<FK_ENTITY> apply(ENTITY entity) {
        return stream().filter(
            getTargetField().equal(getSourceField().get(entity))
        );
    }
}