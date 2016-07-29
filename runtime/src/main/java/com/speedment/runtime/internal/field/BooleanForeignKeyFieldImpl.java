package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.BooleanForeignKeyField;
import com.speedment.runtime.field.method.BooleanGetter;
import com.speedment.runtime.field.method.BooleanSetter;
import com.speedment.runtime.field.method.Finder;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY>    entity type
 * @param <D>         database type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class BooleanForeignKeyFieldImpl<ENTITY, D, FK_ENTITY>  implements BooleanField<ENTITY, D>, BooleanForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final FieldIdentifier<ENTITY> identifier;
    private final BooleanGetter<ENTITY> getter;
    private final BooleanSetter<ENTITY> setter;
    private final Finder<ENTITY, FK_ENTITY> finder;
    private final TypeMapper<D, Boolean> typeMapper;
    private final boolean unique;
    
    public BooleanForeignKeyFieldImpl(FieldIdentifier<ENTITY> identifier, BooleanGetter<ENTITY> getter, BooleanSetter<ENTITY> setter, Finder<ENTITY, FK_ENTITY> finder, TypeMapper<D, Boolean> typeMapper, boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.finder     = requireNonNull(finder);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public FieldIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public BooleanSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public BooleanGetter<ENTITY> getter() {
        return getter;
    }
    
    @Override
    public Finder<ENTITY, FK_ENTITY> finder() {
        return finder;
    }
    
    @Override
    public TypeMapper<D, Boolean> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
}