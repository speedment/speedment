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
package com.speedment.common.singletonstream.internal;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 *
 * @author Per Minborg
 * @since  1.0.0
 */
public final class SingletonUtil {
    
    public static final boolean STRICT = true;

    public static void trip(Class<?> trippingClass, String msg) {
        LOGGER.warn(trippingClass.getName() + ", " + msg);
    }
    
    private static final String TRIPWIRE_PROPERTY = "org.openjdk.java.util.stream.tripwire";
    private static final Logger LOGGER = LoggerManager.getLogger(SingletonUtil.class);
    
    /**
     * Should debugging checks be enabled?
     */
    public static final boolean TRIPWIRE_ENABLED = 
        AccessController.doPrivileged(
            (PrivilegedAction<Boolean>) () -> Boolean.getBoolean(TRIPWIRE_PROPERTY)
        );

    private SingletonUtil() {}
}