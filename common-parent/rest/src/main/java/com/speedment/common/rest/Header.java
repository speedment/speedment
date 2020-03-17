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
package com.speedment.common.rest;

/**
 * A header parameter that will become part of the connection header. This can
 * be used to pass configuration options such as expected result type and the
 * format of the input.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class Header extends AbstractOption {

    /**
     * Returns a new {@code Header} option. This method is intended to be 
     * imported statically.
     * 
     * @param key    the key
     * @param value  the value
     * @return       the created header
     */
    public static Header header(String key, String value) {
        return new Header(key, value);
    }

    @Override
    public Type getType() {
        return Option.Type.HEADER;
    }
    
    private Header(String key, String value) {
        super(key, value);
    }
}