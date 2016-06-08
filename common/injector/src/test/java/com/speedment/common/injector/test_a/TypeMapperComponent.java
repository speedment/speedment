package com.speedment.common.injector.test_a;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class TypeMapperComponent {
    
    private final Map<Class<?>, TypeMapper<?, ?>> toDatabase;
    private final Map<Class<?>, TypeMapper<?, ?>> toJava;

    public TypeMapperComponent() {
        this.toDatabase = new ConcurrentHashMap<>();
        this.toJava     = new ConcurrentHashMap<>();
    }
    
    public <DB_TYPE, JAVA_TYPE> void install(
            Class<DB_TYPE> databaseType, 
            Class<JAVA_TYPE> javaType, 
            TypeMapper<DB_TYPE, JAVA_TYPE> mapper) {
        
        toDatabase.put(databaseType, mapper);
        toJava.put(javaType, mapper);
    }
    
    public Map<Class<?>, TypeMapper<?, ?>> toDatabaseTypeMappers() {
        return toDatabase;
    }
    
    public Map<Class<?>, TypeMapper<?, ?>> toJavaTypeMappers() {
        return toJava;
    }
}