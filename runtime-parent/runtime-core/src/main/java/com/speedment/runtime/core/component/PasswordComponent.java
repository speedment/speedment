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
package com.speedment.runtime.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.Dbms;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * A component that stores the password for all the connected 
 * {@link Dbms dbmses} during this session. Passwords are never
 * stored on disk for security reasons.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@InjectKey(PasswordComponent.class)
public interface PasswordComponent {
    
    /**
     * Stores the specified password for the specified dbmsName. 
     * If the dbmsName already had a stored password, that password 
     * is removed and will no longer exist in memory.
     * <p>
     * <b>Warning:</b> Note that the password might still be stored 
     * in the String cache somewhere else in the JVM memory.
     * 
     * @param dbmsName  the dbms name used as a key
     * @param password  the password associated with that dbms
     */
    void put(String dbmsName, char[] password);
    
    /**
     * Stores the specified password for the specified dbmsName. This
     * is equivalent to calling {@code put(dbms.getName(), password)}.
     * If the dbmsName already had a stored password, that password 
     * is removed and will no longer exist in memory.
     * <p>
     * <b>Warning:</b> Note that the password might still be stored 
     * in the String cache somewhere else in the JVM memory.
     * 
     * @param dbms      the dbms used as a key
     * @param password  the password associated with that dbms
     */
    default void put(Dbms dbms, char[] password) {
        requireNonNull(dbms);
        // password is nullable
        put(dbms.getId(), password);
    }
    
    /**
     * Returns the password associated with the specified dbms.
     * 
     * @param dbmsName  the dbms name used as a key
     * @return          the associated password or empty
     */
    Optional<char[]> get(String dbmsName);
    
    /**
     * Returns the password associated with the specified dbms.
     * This is equivalent to calling {@code get(dbms.getName())}.
     * 
     * @param dbms  the dbms used as a key
     * @return      the associated password or empty
     */
    default Optional<char[]> get(Dbms dbms) {
        requireNonNull(dbms);
        return get(dbms.getId());
    }
}