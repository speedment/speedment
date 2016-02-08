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
package com.speedment.config.db.mapper.string;

import com.speedment.config.db.mapper.TypeMapper;

/**
 *
 * @author Maria Sparenberg
 * @author Patrick Hobusch
 */
public class TrueFalseStringToBooleanMapper implements TypeMapper<String, Boolean> {

    @Override
    public String getLabel() {
        return "True/False to Boolean";
    }
    
    @Override
    public Class<Boolean> getJavaType() {
        return Boolean.class;
    }

    @Override
    public Class<String> getDatabaseType() {
        return String.class;
    }

    @Override
    public Boolean toJavaType(String value) {
        return value == null ? null : Boolean.valueOf(value);
    }

    @Override
    public String toDatabaseType(Boolean value) {
        return value == null ? null : String.valueOf(value);
    }
    
    @Override
    public boolean isIdentityMapper() {
        return false;
    }

}