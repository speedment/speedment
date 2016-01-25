package com.speedment.internal.util.stream;

import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 *
 * @author Per Minborg
 */
public class SingletonUtil {

    private static final Logger LOGGER = LoggerManager.getLogger(SingletonUtil.class);

    static final int SIZE = 1;
    static final boolean STRICT = false;

    private static final String TRIPWIRE_PROPERTY = "org.openjdk.java.util.stream.tripwire";

    /**
     * Should debugging checks be enabled?
     */
    static final boolean TRIPWIRE_ENABLED = AccessController.doPrivileged(
            (PrivilegedAction<Boolean>) () -> Boolean.getBoolean(TRIPWIRE_PROPERTY));

    static void trip(Class<?> trippingClass, String msg) {
        LOGGER.warn(trippingClass.getName() + ", " + msg);
    }

}
