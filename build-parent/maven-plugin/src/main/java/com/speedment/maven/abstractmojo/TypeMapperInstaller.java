/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.maven.abstractmojo;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.maven.typemapper.Mapping;
import com.speedment.runtime.typemapper.TypeMapper;
import org.apache.maven.plugin.MojoExecutionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;

@InjectKey(TypeMapperInstaller.class)
public final class TypeMapperInstaller { // This class must be public

    private final Mapping[] mappings;

    public TypeMapperInstaller(Mapping[] mappings) {
        this.mappings = mappings; // Nullable
    }

    @ExecuteBefore(RESOLVED)
    public void installInTypeMapper(
        final Injector injector,
        final @WithState(INITIALIZED) TypeMapperComponent typeMappers
    ) throws MojoExecutionException {
        if (mappings != null) {
            for (final Mapping mapping : mappings) {
                final Class<?> databaseType = databaseTypeFromMapping(injector, mapping);

                try {
                    final Class<?> uncasted = injector.classLoader()
                        .loadClass(mapping.getImplementation());

                    @SuppressWarnings("unchecked") final Class<TypeMapper<?, ?>> casted
                        = (Class<TypeMapper<?, ?>>) uncasted;
                    final Constructor<TypeMapper<?, ?>> constructor
                        = casted.getConstructor();

                    final Supplier<TypeMapper<?, ?>> supplier = () -> tryGetTypeMapper(constructor);

                    typeMappers.install(databaseType, supplier);
                } catch (final ClassNotFoundException ex) {
                    throw new MojoExecutionException(
                        AbstractSpeedmentMojo.SPECIFIED_CLASS + "'" + mapping.getImplementation()
                            + "' could not be found on class path. Has the "
                            + "dependency been configured properly?", ex
                    );
                } catch (final ClassCastException ex) {
                    throw new MojoExecutionException(
                        AbstractSpeedmentMojo.SPECIFIED_CLASS + "'" + mapping.getImplementation()
                            + "' does not implement the '"
                            + TypeMapper.class.getSimpleName() + "'-interface.",
                        ex
                    );
                } catch (final NoSuchMethodException | TypeMapperInstantiationException ex) {
                    throw new MojoExecutionException(
                        AbstractSpeedmentMojo.SPECIFIED_CLASS + "'" + mapping.getImplementation()
                            + "' could not be instantiated. Does it have a "
                            + "default constructor?", ex
                    );
                }
            }
        }
    }

    private TypeMapper<?, ?> tryGetTypeMapper(Constructor<TypeMapper<?, ?>> constructor) {
        try {
            return constructor.newInstance();
        } catch (final IllegalAccessException
            | IllegalArgumentException
            | InstantiationException
            | InvocationTargetException ex) {

            throw new TypeMapperInstantiationException(ex);
        }
    }

    private Class<?> databaseTypeFromMapping(Injector injector, Mapping mapping) throws MojoExecutionException {
        Class<?> databaseType;
        try {
            databaseType = injector.classLoader()
                .loadClass(mapping.getDatabaseType());

        } catch (final ClassNotFoundException ex) {
            throw new MojoExecutionException(
                "Specified database type '" + mapping.getDatabaseType() + "' "
                    + "could not be found on class path. Make sure it is a "
                    + "valid JDBC type for the chosen connector.", ex
            );
        } catch (final ClassCastException ex) {
            throw new MojoExecutionException(
                "An unexpected ClassCastException occurred.", ex
            );
        }
        return databaseType;
    }

    private static final class TypeMapperInstantiationException extends RuntimeException {

        private static final long serialVersionUID = -8267239306656063289L;

        private TypeMapperInstantiationException(Throwable thrw) {
            super(thrw);
        }
    }

}
