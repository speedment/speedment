package com.speedment.core.field;

import com.speedment.api.field.ReferenceComparableForeignKeyStringField;
import com.speedment.api.field.methods.Getter;
import com.speedment.api.field.methods.Setter;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <FK>
 */
public class ReferenceComparableForeignKeyStringFieldImpl<ENTITY, FK> 
    extends ReferenceComparableStringFieldImpl<ENTITY> 
    implements ReferenceComparableForeignKeyStringField<ENTITY, FK> {
    
    private final Getter<ENTITY, FK> finder;

    public ReferenceComparableForeignKeyStringFieldImpl(String columnName, Getter<ENTITY, String> getter, Setter<ENTITY, String> setter, Getter<ENTITY, FK> finder) {
        super(columnName, getter, setter);
        this.finder = finder;
    }

    @Override
    public Getter<ENTITY, FK> finder() {
        return finder;
    }

    @Override
    public FK findFrom(ENTITY entity) {
        return finder.apply(entity);
    }
}