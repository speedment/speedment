package com.speedment.plugins.enums;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.generator.translator.provider.StandardCodeGenerationComponent;
import com.speedment.generator.translator.provider.StandardTypeMapperComponent;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.tool.propertyeditor.provider.DelegatePropertyEditorComponent;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EnumGeneratorComponentTest {

    @Test
    void include() {
        final Set<Class<?>> classes = EnumGeneratorComponent.include().injectables().collect(toSet());
        assertTrue(classes.contains(StandardTypeMapperComponent.class));
        assertTrue(classes.contains(StandardCodeGenerationComponent.class));
        assertTrue(classes.contains(DelegatePropertyEditorComponent.class));
    }

    @Test
    void installDecorators() throws InstantiationException {
        final InjectorBuilder builder = Injector.builder();
        EnumGeneratorComponent.include().injectables().forEach(builder::withComponent);
        builder.withComponent(EnumGeneratorComponent.class);
        final Injector injector = builder.build();

        final TypeMapperComponent typeMapperComponent = injector.getOrThrow(TypeMapperComponent.class);

        final Set<Class<?>> typeMapperComponentClasses = typeMapperComponent.stream().map(Object::getClass).collect(toSet());
        assertTrue(typeMapperComponentClasses.contains(StringToEnumTypeMapper.class));
        assertTrue(typeMapperComponentClasses.contains(IntegerToEnumTypeMapper.class));
    }
}