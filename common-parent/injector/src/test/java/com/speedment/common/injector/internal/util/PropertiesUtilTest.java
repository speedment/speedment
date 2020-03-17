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
package com.speedment.common.injector.internal.util;

import com.speedment.common.injector.MyInjectorProxy;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.exception.InjectorException;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

final class PropertiesUtilTest {

    private static final Logger LOGGER = LoggerManager.getLogger(PropertiesUtilTest.class);

    public static final class Foo {
        @Config(name = "a", value = "missing")
        public String val;
    }

    public static final class Bar {
        @Config(name = "a", value = "missing")
        private String val; // Cannot access
    }

    @Test
    void loadProperties() {
        final Properties properties = PropertiesUtil.loadProperties(LOGGER, file("test.properties"));
        assertNotNull(properties);
        assertEquals("1", properties.getProperty("a"));
    }

    @Test
    void loadPropertiesNoFile() {
        final Properties properties = PropertiesUtil.loadProperties(LOGGER, new File("jHS%3563qGHGJHg"));
        assertNotNull(properties);
        assertTrue(properties.isEmpty());
    }

    @Test
    @Disabled("How do we simulate IOException on a file?")
    void loadPropertiesCorrupted() {
        assertThrows(InjectorException.class, () -> PropertiesUtil.loadProperties(LOGGER, file("corrupted.properties")));
    }

    @Test
    void configureParams() {
        final Foo foo = new Foo();
        final Properties properties = PropertiesUtil.loadProperties(LOGGER, file("test.properties"));
        PropertiesUtil.configureParams(foo, properties, new MyInjectorProxy());
        assertEquals("1", foo.val);
    }

    @Test
    void configureParamsIllegal() {
        final Bar bar = new Bar();
        final Properties properties = PropertiesUtil.loadProperties(LOGGER, file("test.properties"));
        assertThrows(InjectorException.class, () -> PropertiesUtil.configureParams(bar, properties, new MyInjectorProxy()));
    }

    private File file(String name) {
        final URL resourceUrl = PropertiesUtilTest.class.getResource(FileSystems.getDefault().getSeparator() + name);
        return new File(resourceUrl.getFile());
    }

}