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
package com.speedment.tool.core.util;


import javafx.util.StringConverter;

/**
 * A {@link StringConverter} that simply returns the input string.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class IdentityStringConverter extends StringConverter<String> {

    @Override
    public String toString(String str) {
        return str;
    }

    @Override
    public String fromString(String str) {
        return str;
    }
}