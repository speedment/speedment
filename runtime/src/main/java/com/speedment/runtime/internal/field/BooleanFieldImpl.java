package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.method.BooleanGetter;
import com.speedment.runtime.field.method.BooleanSetter;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class BooleanFieldImpl<ENTITY, D> implements BooleanField<ENTITY, D> {
    
    private final FieldIdentifier<ENTITY> identifier;
    private final BooleanGetter<ENTITY> getter;
    private final BooleanSetter<ENTITY> setter;
    private final TypeMapper<D, Boolean> typeMapper;
    private final boolean unique;
    
    public BooleanFieldImpl(FieldIdentifier<ENTITY> identifier, BooleanGetter<ENTITY> getter, BooleanSetter<ENTITY> setter, TypeMapper<D, Boolean> typeMapper, boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
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
    public TypeMapper<D, Boolean> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
}