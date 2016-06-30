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
package com.speedment.runtime.config.mapper.integer;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;

/**
 *
 * @author  Roberts Vartins
 * @since   2.3.5
 */
@Api(version = "3.0")
public class IntegerZeroOneToBooleanMapper implements TypeMapper<Integer, Boolean> {

    @Override
    public String getLabel() {
        return "Integer (0|1) to Boolean";
    }

    @Override
    public Class<Boolean> getJavaType() {
        return Boolean.class;
    }

    @Override
    public Class<Integer> getDatabaseType() {
        return Integer.class;
    }

    @Override
    public Boolean toJavaType(Integer value) {
        return value == null ? null : value != 0;
    }

    @Override
    public Integer toDatabaseType(Boolean value) {
        return value == null ? null : (value ? 1 : 0);
    }

    @Override
    public boolean isIdentityMapper() {
        return false;
    }
}