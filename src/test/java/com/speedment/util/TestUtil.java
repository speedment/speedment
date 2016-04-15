/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.Assert.fail;

/**
 *
 * @author Per Minborg
 */
public class TestUtil {

    public static <T> void assertNonInstansiable(Class<T> clazz) {
        try {
            final Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            final T instance = constructor.newInstance();
            fail("The class " + clazz.getName() + " should not be instansiable");
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // Ignore
        }
    }

}
