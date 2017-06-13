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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;

/**
 *
 * @author Duncan
 */
public final class Hash {

    /**
     * Creates and returns an MD5 from the given UTF-8 encoded String.
     *
     * @param str UTF-8 encoded String
     * @return an MD5 from the given UTF-8 encoded String
     */
    public static String md5(String str) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] mdbytes = md.digest(str.getBytes(StandardCharsets.UTF_8));

            // convert the byte to hex format method 1
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("MD5 algorithm not supported.", ex);
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private Hash() {
        instanceNotAllowed(getClass());
    }
}
