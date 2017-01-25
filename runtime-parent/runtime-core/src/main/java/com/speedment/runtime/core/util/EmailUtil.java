package com.speedment.runtime.core.util;

import com.speedment.runtime.core.internal.util.InternalEmailUtil;
import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;

/**
 * Reads and writes to the internal file where a user's email address is stored.
 * This is used by the UI to know if it has already asked for a user's email
 * address.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class EmailUtil {

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

    /**
     * Should not be instantiated.
     */
    private EmailUtil() {
        instanceNotAllowed(getClass());
    }
}