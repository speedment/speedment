package com.speedment.internal.core.config.mapper.identity;

import com.speedment.config.mapper.TypeMapper;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @param <T> type
 */
public abstract class AbstractIdentityMapper<T> implements TypeMapper<T, T> {
    
    private final Class<T> type;
    
    protected AbstractIdentityMapper(Class<T> type) {
        this.type = requireNonNull(type);
    }

    @Override
    public final Class<T> getJavaType() {
        return type;
    }

    @Override
    public final Class<T> getDatabaseType() {
        return type;
    }

    @Override
    public final T toJavaType(T value) {
        return value;
    }

    @Override
    public final T toDatabaseType(T value) {
        return value;
    }
}