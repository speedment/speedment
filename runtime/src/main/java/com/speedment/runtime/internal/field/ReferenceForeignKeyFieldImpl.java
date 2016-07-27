
package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.ReferenceForeignKeyField;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.field.trait.ReferenceForeignKeyFieldTrait;
import com.speedment.runtime.internal.field.trait.FieldTraitImpl;
import com.speedment.runtime.internal.field.trait.ReferenceFieldImpl;
import com.speedment.runtime.internal.field.trait.ReferenceForeignKeyFieldTraitImpl;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;

import java.util.Optional;

import com.speedment.runtime.field.method.SetToReference;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import com.speedment.runtime.field.method.ReferenceGetter;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import com.speedment.runtime.field.method.ReferenceSetter;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import com.speedment.runtime.field.method.Finder;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ReferenceForeignKeyFieldImpl<ENTITY, D, V, FK> implements ReferenceForeignKeyField<ENTITY, D, V, FK> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, V> referenceField;
    private final ReferenceForeignKeyFieldTrait<ENTITY, D, FK> referenceForeignKeyField;

    public ReferenceForeignKeyFieldImpl(
            FieldIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, V> getter,
            ReferenceSetter<ENTITY, V> setter,
            Finder<ENTITY, FK> finder,
            TypeMapper<D, V> typeMapper,
            boolean unique
    ) {
        requireNonNulls(identifier, getter, setter, finder, typeMapper);
        field = new FieldTraitImpl(identifier, unique);
        referenceField = new ReferenceFieldImpl<>(field, getter, setter, typeMapper);
        referenceForeignKeyField = new com.speedment.runtime.internal.field.trait.ReferenceForeignKeyFieldImpl<>(finder);
    }

    @Override
    public FieldIdentifier<ENTITY> getIdentifier() {
        return referenceField.getIdentifier();
    }
    
    @Override
    public boolean isUnique() {
        return field.isUnique();
    }
    
    @Override
    public Optional<Column> findColumn(Project project) {
        return Optional.of(DocumentDbUtil.referencedColumn(project, getIdentifier()));
    }

    @Override
    public ReferenceSetter<ENTITY, V> setter() {
        return referenceField.setter();
    }

    @Override
    public ReferenceGetter<ENTITY, V> getter() {
        return referenceField.getter();
    }

    @Override
    public Finder<ENTITY, FK> finder() {
        return referenceForeignKeyField.finder();
    }

    @Override
    public TypeMapper<D, V> typeMapper() {
        return referenceField.typeMapper();
    }

    @Override
    public SetToReference<ENTITY, V> setTo(V value) {
        return referenceField.setTo(value);
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, V> isNull() {
        return referenceField.isNull();
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, V> isNotNull() {
        return referenceField.isNotNull();
    }

}
