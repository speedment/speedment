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
package com.speedment.internal.util;

import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.prefs.Preferences;

/**
 *
 * @author Emil Forslund
 */
public final class EmailUtil {
    
    private final static String FIELD_NAME = "user_mail";
    private final static String DEFAULT_EMAIL = "no-mail-specified";
    private final static Preferences PREFERENCES = Preferences.userNodeForPackage(EmailUtil.class);
    
    public static boolean hasEmail() {
        return !DEFAULT_EMAIL.equals(PREFERENCES.get(FIELD_NAME, DEFAULT_EMAIL));
    }
    
    public static String getEmail() {
        return PREFERENCES.get(FIELD_NAME, DEFAULT_EMAIL);
    }
    
    public static void setEmail(String email) {
        PREFERENCES.put(FIELD_NAME, email == null ? DEFAULT_EMAIL : email);
    }
    
    private EmailUtil() {
        instanceNotAllowed(getClass());
    }
}