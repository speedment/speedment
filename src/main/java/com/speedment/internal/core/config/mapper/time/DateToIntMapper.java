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
package com.speedment.internal.core.config.mapper.time;

import java.sql.Date;

import com.speedment.config.db.mapper.TypeMapper;


/**
 *
 * @author Emil Forslund
 */
public class DateToIntMapper implements TypeMapper<Date, Integer> {

    @Override
    public Class<Integer> getJavaType() {
        return Integer.class;
    }

    @Override
    public Class<Date> getDatabaseType() {
        return Date.class;
    }

    @Override
    public Integer toJavaType(Date value) {
        return value == null ? null : (int) (value.getTime() / 1000);
    }

    @Override
    public Date toDatabaseType(Integer value) {
        return value == null ? null : new Date(value * 1000L);
    }
    
    @Override
    public boolean isIdentityMapper() {
        return false;
    }
}