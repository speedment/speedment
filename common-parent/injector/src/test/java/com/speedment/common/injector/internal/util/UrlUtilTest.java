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
package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

final class UrlUtilTest {

    private static final String URL_STRING = "http://www,speedment.com";

    @Test
    void tryCreateURL() {
        final Object object = UrlUtil.tryCreateURL(URL_STRING);
        if (object instanceof URL) {
            final URL url = (URL) object;
            assertEquals(URL_STRING, url.toString());
        } else {
            fail(object.getClass().getName() + " does not extend " + URL.class.getName());
        }
    }

    @Test
    void tryCreateURLIllegal() {
        assertThrows(IllegalArgumentException.class, () -> UrlUtil.tryCreateURL("a"));
    }

}