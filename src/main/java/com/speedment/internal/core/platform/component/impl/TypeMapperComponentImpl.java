package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.TypeMapperComponent;
import com.speedment.config.mapper.TypeMapper;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
public final class TypeMapperComponentImpl extends Apache2AbstractComponent implements TypeMapperComponent {

    private final Set<TypeMapper<?, ?>> mappers;

    /**
     * Constructs the component.
     * 
     * @param speedment  the speedment instance
     */
    public TypeMapperComponentImpl(Speedment speedment) {
        super(speedment);
        this.mappers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }
    
    @Override
    public Class<TypeMapperComponent> getComponentClass() {
        return TypeMapperComponent.class;
    }

    @Override
    public <DB_TYPE, JAVA_TYPE> void install(TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper) {
        mappers.add(typeMapper);
    }

    @Override
    public Stream<TypeMapper<?, ?>> stream() {
        return mappers.stream();
    }
}