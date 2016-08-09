package com.speedment.plugins.enums;

import com.speedment.common.injector.Injector;
import com.speedment.plugins.enums.internal.GeneratedEnumType;
import com.speedment.common.injector.annotation.Inject;
import static com.speedment.plugins.enums.internal.GeneratedEntityDecorator.FROM_DATABASE_METHOD;
import com.speedment.plugins.enums.internal.EnumGeneratorUtil;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.Lazy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the enum type
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   1.0.0
 */
public final class StringToEnumTypeMapper<T extends Enum<T>> implements TypeMapper<String, T> {

    private final Lazy<Class<?>> cachedEnum;
    private @Inject Injector injector;
    
    public StringToEnumTypeMapper() {
        cachedEnum = Lazy.create();
    }
    
    @Override
    public String getLabel() {
        return "String to Enum";
    }

    @Override
    public Type getJavaType(Column column) {
        requireNonNull(injector, 
            StringToEnumTypeMapper.class.getSimpleName() + 
            ".getJavaType(Column) is not available if instantiated without injector."
        );
        
        return new GeneratedEnumType(
            EnumGeneratorUtil.enumNameOf(column, injector), 
            EnumGeneratorUtil.enumConstantsOf(column)
        );
    }

    @Override
    public <ENTITY> T toJavaType(Column column, Class<ENTITY> entityType, String value) {
        if (value == null) {
            return null;
        } else {
            final Class<?> enumClass = cachedEnum.getOrCompute(
                () -> EnumGeneratorUtil.classesIn(entityType)

                    // Include only enum subclasses
                    .filter(Enum.class::isAssignableFrom)
                    
                    // Include only enums with the correct name
                    .filter(c -> c.getSimpleName().equalsIgnoreCase(
                        column.getJavaName().replace("_", "")
                    ))

                    // Include only enums with a method called fromDatabase()
                    // that takes the right parameters
                    .filter(c -> Stream.of(c.getMethods())
                        .filter(m -> m.getName().equals("fromDatabase"))
                        .filter(m -> {
                            final Class<?>[] params = m.getParameterTypes();
                            return params.length == 1 
                                && params[0] == column.findDatabaseType();
                        })
                        .findAny().isPresent()
                    )

                    // Return it as the enumClass or throw an exception.
                    .findAny()
                    .orElse(null)
            );

            final Method method;
            try {
                method = enumClass.getMethod(FROM_DATABASE_METHOD, String.class);
            } catch (final NoSuchMethodException ex) {
                throw new SpeedmentException(
                    "Could not find generated '" + FROM_DATABASE_METHOD + 
                    "'-method in enum class '" + enumClass.getName() + "'.", ex
                );
            }

            try {
                @SuppressWarnings("unchecked")
                final T result = (T) method.invoke(null, value);
                return result;
            } catch (final IllegalAccessException 
                         | IllegalArgumentException 
                         | InvocationTargetException ex) {
                throw new SpeedmentException(
                    "Error executing '" + FROM_DATABASE_METHOD + 
                    "' in generated enum class '" + enumClass.getName() + "'.",
                    ex
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