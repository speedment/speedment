/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.annotation.Config;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import com.speedment.common.logger.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class PropertiesUtil {

    public static Properties loadProperties(Logger logger, File configFile) {
        final Properties properties = new Properties();
        if (configFile.exists() && configFile.canRead()) {

            try (final InputStream in = new FileInputStream(configFile)) {
                properties.load(in);
            } catch (final IOException ex) {
                final String err = "Error loading default settings from "
                    + configFile.getAbsolutePath() + "-file.";
                logger.error(ex, err);
                throw new RuntimeException(err, ex);
            }
        } else {
            logger.info(
                "No configuration file '"
                + configFile.getAbsolutePath() + "' found."
            );
        }

        return properties;
    }
    
    public static <T> void configureParams(T instance, Properties properties) {
        traverseFields(instance.getClass())
            .filter(f -> f.isAnnotationPresent(Config.class))
            .forEach(f -> {
                final Config config = f.getAnnotation(Config.class);

                final String serialized;
                if (properties.containsKey(config.name())) {
                    serialized = properties.getProperty(config.name());
                } else {
                    serialized = config.value();
                }

                f.setAccessible(true);

                try {
                    if (boolean.class == f.getType() 
                    || Boolean.class.isAssignableFrom(f.getType())) {
                        f.set(instance, Boolean.parseBoolean(serialized));
                    } else if (byte.class == f.getType() 
                    || Byte.class.isAssignableFrom(f.getType())) {
                        f.set(instance, Byte.parseByte(serialized));
                    } else if (short.class == f.getType() 
                    || Short.class.isAssignableFrom(f.getType())) {
                        f.set(instance, Short.parseShort(serialized));
                    } else if (int.class == f.getType() 
                    || Integer.class.isAssignableFrom(f.getType())) {
                        f.set(instance, Integer.parseInt(serialized));
                    } else if (long.class == f.getType() 
                    || Long.class.isAssignableFrom(f.getType())) {
                        f.set(instance, Long.parseLong(serialized));
                    } else if (float.class == f.getType() 
                    || Float.class.isAssignableFrom(f.getType())) {
                        f.set(instance, Float.parseFloat(serialized));
                    } else if (double.class == f.getType() 
                    || Double.class.isAssignableFrom(f.getType())) {
                        f.set(instance, Double.parseDouble(serialized));
                    } else if (String.class.isAssignableFrom(f.getType())) {
                        f.set(instance, serialized);
                    } else if (char.class == f.getType() 
                    || Character.class.isAssignableFrom(f.getType())) {
                        if (serialized.length() == 1) {
                            f.set(instance, serialized.charAt(0));
                        } else {
                            throw new IllegalArgumentException(
                                "Value '" + serialized + "' is to long to be " + 
                                "parsed into a field of type '" +
                                f.getType().getName() + "'."
                            );
                        }
                    } else if (File.class.isAssignableFrom(f.getType())) {
                        f.set(instance, new File(serialized));
                    } else if (URL.class.isAssignableFrom(f.getType())) {
                        try {
                            f.set(instance, new URL(serialized));
                        } catch (final MalformedURLException ex) {
                            throw new IllegalArgumentException(
                                "Specified URL '" + serialized + "' is " + 
                                "malformed.", ex
                            );
                        }
                    }
                } catch (final IllegalAccessException 
                             | IllegalArgumentException ex) {
                    
                    throw new RuntimeException(
                        "Failed to set config parameter '" + config.name() +
                        "' in class '" + instance.getClass().getName() + 
                        "'.", ex
                    );
                }
            });
    }
    
    private PropertiesUtil() {}
}