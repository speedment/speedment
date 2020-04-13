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
package com.speedment.runtime.application;

import com.speedment.runtime.application.provider.DefaultApplicationBuilder;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.application.provider.DefaultApplicationMetadata;
import com.speedment.runtime.application.provider.EmptyApplicationMetadata;

/**
 * Builder class for producing new {@link Speedment} instances.
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public final class ApplicationBuilders {

    private ApplicationBuilders() {
    }

    /**
     * Creates and returns a new empty {@code ApplicationBuilder}.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @return a new empty ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        empty() {
        return (BUILDER) new DefaultApplicationBuilder(
            EmptyApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new empty {@code ApplicationBuilder}.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @param classLoader the class loader to use in the injector
     * @return a new empty ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        empty(ClassLoader classLoader) {
        return (BUILDER) new DefaultApplicationBuilder(
            classLoader,
            EmptyApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new standard ApplicationBuilder. The configuration
     * is read from a JSON file.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @return a new standard ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        standard() {
        return (BUILDER) new DefaultApplicationBuilder(
            DefaultApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new standard ApplicationBuilder. The configuration
     * is read from a JSON file.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @param classLoader the class loader to use in the injector
     * @return a new standard ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        standard(ClassLoader classLoader) {
        return (BUILDER) new DefaultApplicationBuilder(
            classLoader,
            DefaultApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new ApplicationBuilder configured with the given
     * ApplicationMetadata class.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @param applicationMetadataclass with configuration
     *
     * @return a new ApplicationBuilder configured with the given
     * ApplicationMetadata class
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        create(Class<? extends ApplicationMetadata> applicationMetadataclass) {
        return (BUILDER) new DefaultApplicationBuilder(
            applicationMetadataclass
        );
    }

}
