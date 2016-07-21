package com.speedment.plugins.enums;

import com.speedment.common.injector.Injector;
import com.speedment.plugins.enums.internal.GeneratedEnumTypeToken;
import com.speedment.common.injector.annotation.Inject;
import static com.speedment.plugins.enums.internal.GeneratedEntityDecorator.FROM_DATABASE_METHOD;
import com.speedment.plugins.enums.internal.EnumGeneratorUtil;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.exception.SpeedmentException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @param <T>  the enum type
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   1.0.0
 */
public final class StringToEnumTypeMapper<T extends Enum<T>> implements TypeMapper<String, T> {

    private @Inject Injector injector;
    
    @Override
    public String getLabel() {
        return "String to Enum";
    }

    @Override
    public TypeToken getJavaType(Column column) {
        requireNonNull(injector, 
            StringToEnumTypeMapper.class.getSimpleName() + 
            ".getJavaType(Column) is not available if instantiated without injector."
        );
        
        return new GeneratedEnumTypeToken(
            EnumGeneratorUtil.enumNameOf(column, injector), 
            EnumGeneratorUtil.enumConstantsOf(column)
        );
    }

    @Override
    public <ENTITY> T toJavaType(Column column, Class<ENTITY> entityType, String value) {
        if (value == null) {
            return null;
        } else {
            final String expectedName = EnumGeneratorUtil.enumNameOf(column, injector);
            final Class<?> enumClass = Stream.of(entityType.getClasses())
                .filter(c -> c.getName().equals(expectedName))
                .findAny()
                .orElseThrow(() -> new SpeedmentException(
                    "Could not find the expected inner enum class '" + expectedName + 
                    "' in entity inteface '" + entityType.getName() + "'."
                ));

            final Method method;
            try {
                method = enumClass.getMethod(FROM_DATABASE_METHOD, String.class);

            } catch (final NoSuchMethodException ex) {
                throw new SpeedmentException(
                    "Could not find generated '" + FROM_DATABASE_METHOD + "'-method in enum class '" + 
                    expectedName + "'."
                );
            }

            try {
                @SuppressWarnings("unchecked")
                final T result = (T) method.invoke(null, value);
                return result;
            } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new SpeedmentException(
                    "Error executing '" + FROM_DATABASE_METHOD + 
                    "' in generated enum class '" + expectedName + "'."
                );
            }
        }
    }

    @Override
    public String toDatabaseType(T constant) {
        if (constant == null) {
            return null;
        } else {
            final Class<?> enumClass = constant.getClass();

            final Method method;
            try {
                method = enumClass.getMethod(FROM_DATABASE_METHOD, String.class);

            } catch (final NoSuchMethodException ex) {
                throw new SpeedmentException(
                    "Could not find generated '" + FROM_DATABASE_METHOD + "'-method in enum class '" + 
                    constant.getClass().getName() + "'."
                );
            }

            try {
                @SuppressWarnings("unchecked")
                final String result = (String) method.invoke(constant);
                return result;
            } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new SpeedmentException(
                    "Error executing '" + FROM_DATABASE_METHOD + 
                    "' in generated enum class '" + constant.getClass().getName() + "'."
                );
            }
        }
    }
}