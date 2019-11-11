package com.speedment.generator.translator.provider;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.generator.translator.internal.component.TypeMapperComponentImpl;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.trait.HasTypeMapper;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class StandardTypeMapperComponent implements TypeMapperComponent {

    private final TypeMapperComponentImpl inner;

    public StandardTypeMapperComponent() {
        this.inner = new TypeMapperComponentImpl();
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        inner.setInjector(injector);
    }

    public void install(Class<?> databaseType, Supplier<TypeMapper<?, ?>> typeMapperConstructor) {
        inner.install(databaseType, typeMapperConstructor);
    }

    public Stream<TypeMapper<?, ?>> mapFrom(Class<?> databaseType) {
        return inner.mapFrom(databaseType);
    }

    public Stream<TypeMapper<?, ?>> stream() {
        return inner.stream();
    }

    public Optional<TypeMapper<?, ?>> get(String absoluteClassName) {
        return inner.get(absoluteClassName);
    }

    public TypeMapper<?, ?> get(HasTypeMapper column) {
        return inner.get(column);
    }

    public <DB_TYPE, JAVA_TYPE> Optional<Class<DB_TYPE>> findDatabaseTypeOf(TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper) {
        return inner.findDatabaseTypeOf(typeMapper);
    }

    public Type typeOf(Column column) {
        return inner.typeOf(column);
    }

    public TypeMapper.Category categoryOf(Column column) {
        return inner.categoryOf(column);
    }
}
