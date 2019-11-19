/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.injector.internal.util;

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.exception.InjectorException;
import com.speedment.common.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;

import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class PropertiesUtil {

    private PropertiesUtil() {}

    public static Properties loadProperties(Logger logger, File configFile) {
        final Properties properties = new Properties();
        if (configFile.exists() && configFile.canRead()) {

            try (final InputStream in = new FileInputStream(configFile)) {
                properties.load(in);
            } catch (final IOException ex) {
                final String err = "Error loading default settings from "
                    + configFile.getAbsolutePath() + "-file.";
                logger.error(ex, err);
                throw new InjectorException(err, ex);
            }
        } else {
            logger.info(
                "No configuration file '"
                + configFile.getAbsolutePath() + "' found."
            );
        }

        return properties;
    }
    
    public static <T> void configureParams(T instance, Properties properties, InjectorProxy injectorProxy) {
        requireNonNull(instance);
        requireNonNull(properties);
        requireNonNull(injectorProxy);

        traverseFields(instance.getClass())
            .filter(f -> f.isAnnotationPresent(Config.class))
            .forEach(f -> configure(instance, properties, injectorProxy, f));
    }

    private static <T> void configure(T instance, Properties properties, InjectorProxy injectorProxy, Field f) {
        final Config config = f.getAnnotation(Config.class);

        final String serialized;
        if (properties.containsKey(config.name())) {
            serialized = properties.getProperty(config.name());
        } else {
            serialized = config.value();
        }

        trySetInjectorProxy(instance, injectorProxy, f, config, serialized);
    }

    private static <T> void trySetInjectorProxy(T instance, InjectorProxy injectorProxy, Field f, Config config, String serialized) {
        final Object object;
        try {
            if (boolean.class == f.getType()
            || Boolean.class.isAssignableFrom(f.getType())) {
                object = Boolean.parseBoolean(serialized);
            } else if (byte.class == f.getType()
            || Byte.class.isAssignableFrom(f.getType())) {
                object = Byte.parseByte(serialized);
            } else if (short.class == f.getType()
            || Short.class.isAssignableFrom(f.getType())) {
                object = Short.parseShort(serialized);
            } else if (int.class == f.getType()
            || Integer.class.isAssignableFrom(f.getType())) {
                object = Integer.parseInt(serialized);
            } else if (long.class == f.getType()
            || Long.class.isAssignableFrom(f.getType())) {
                object = Long.parseLong(serialized);
            } else if (float.class == f.getType()
            || Float.class.isAssignableFrom(f.getType())) {
                object = Float.parseFloat(serialized);
            } else if (double.class == f.getType()
            || Double.class.isAssignableFrom(f.getType())) {
                object = Double.parseDouble(serialized);
            } else if (String.class.isAssignableFrom(f.getType())) {
                object = serialized;
            } else if (char.class == f.getType()
            || Character.class.isAssignableFrom(f.getType())) {
                if (serialized.length() == 1) {
                    object = serialized.charAt(0);
                } else {
                    throw new IllegalArgumentException(
                        "Value '" + serialized + "' is to long to be " +
                        "parsed into a field of type '" +
                        f.getType().getName() + "'."
                    );
                }
            } else if (File.class.isAssignableFrom(f.getType())) {
                object = new File(serialized);
            } else if (URL.class.isAssignableFrom(f.getType())) {
                object = UrlUtil.tryCreateURL(serialized);
            } else {
                // No op
                return;
            }
            injectorProxy.set(f, instance, object);
        } catch (final ReflectiveOperationException ex) {
            throw new InjectorException(
                "Failed to set config parameter '" + config.name() +
                "' in class '" + instance.getClass().getName() +
                "'.", ex
            );
        }
    }

}