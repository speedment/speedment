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
package com.speedment.runtime.core.internal.util;

import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.prefs.Preferences;

/**
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class InternalEmailUtil {

    private final static String FIELD_NAME       = "user_mail";
    private final static String DEFAULT_EMAIL    = "no-mail-specified";
    private final static Preferences PREFERENCES = Preferences.userNodeForPackage(InternalEmailUtil.class);

    public static boolean hasEmail() {
        final String storedEmail = PREFERENCES.get(FIELD_NAME, null);
        if (storedEmail == null) {
            return false;
        }
        return !DEFAULT_EMAIL.equals(storedEmail);
    }

    public static String getEmail() {
        return Optional.ofNullable(
            PREFERENCES.get(FIELD_NAME, null)
        ).orElse(DEFAULT_EMAIL);
    }

    public static void removeEmail() {
        PREFERENCES.remove(FIELD_NAME);
    }

    public static void setEmail(String email) {
        requireNonNull(email);
        PREFERENCES.put(FIELD_NAME, email);
    }

    private InternalEmailUtil() {
        instanceNotAllowed(getClass());
    }
}
