/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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

package com.speedment.internal.core.config.mapper.string;

import java.util.Locale;

import com.speedment.config.mapper.TypeMapper;

/**
*
* @author Maria Sparenberg
* @author Patrick Hobusch
*/
public class StringToLocaleMapper implements TypeMapper<String, Locale> {
   
    @Override
    public Class<Locale> getJavaType() {
        return Locale.class;
    }

    @Override
    public Class<String> getDatabaseType() {
        return String.class;
    }

    @Override
    public Locale toJavaType(String value) {
       return value == null ? null : new Locale(value);
    }

    @Override
    public String toDatabaseType(Locale value) {
       return value == null ? null : value.getLanguage();
    }

    @Override
    public boolean isIdentityMapper() {
        return false;
    }

}