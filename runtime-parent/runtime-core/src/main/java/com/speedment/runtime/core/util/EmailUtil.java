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
package com.speedment.runtime.core.util;

import com.speedment.runtime.core.internal.util.InternalEmailUtil;

/**
 * Reads and writes to the internal file where a user's email address is stored.
 * This is used by the UI to know if it has already asked for a user's email
 * address.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class EmailUtil {

    private EmailUtil() {}

    /**
     * Returns {@code true} if an email address has already been entered.
     * 
     * @return  {@code true} if an email exists, else {@code false}
     */
    public static boolean hasEmail() {
        return InternalEmailUtil.hasEmail();
    }

    /**
     * Returns the email address of the current user.
     * 
     * @return  the email address
     */
    public static String getEmail() {
        return InternalEmailUtil.getEmail();
    }

    /**
     * Removes the email address stored on the computer.
     */
    public static void removeEmail() {
        InternalEmailUtil.removeEmail();
    }

    /**
     * Overwrites the email address stored (if any) with the one specified.
     * 
     * @param email  the new email address to store
     */
    public static void setEmail(String email) {
        InternalEmailUtil.setEmail(email);
    }

}