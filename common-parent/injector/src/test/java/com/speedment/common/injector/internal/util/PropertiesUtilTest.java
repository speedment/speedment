package com.speedment.common.injector.internal.util;

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.exception.InjectorException;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static final class MyInjectorProxy implements InjectorProxy {
        @Override
        public boolean isApplicable(Class<?> clazz) {
            return false;
        }

        @Override
        public void set(Field field, Object instance, Object value) throws IllegalAccessException {
            field.set(instance, value);
        }

        @Override
        public <T> T newInstance(Constructor<T> constructor, Object... args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object invoke(Method method, Object obj, Object... args) throws IllegalAccessException, InvocationTargetException {
            throw new UnsupportedOperationException();
        }
    }

}