/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
