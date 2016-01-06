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
package com.speedment.internal.core.config.mapper.time;

import java.sql.Date;

import com.speedment.config.db.mapper.TypeMapper;


/**
 *
 * @author Maria Sparenberg
 * @author Patrick Hobusch
 */
public class DateToLongMapper implements TypeMapper<Date, Long> {

    @Override
    public Class<Long> getJavaType() {
        return Long.class;
    }

    @Override
    public Class<Date> getDatabaseType() {
        return Date.class;
    }

    @Override
    public Long toJavaType(Date value) {
        return value == null ? null : value.getTime();
    }

    @Override
    public Date toDatabaseType(Long value) {
        return value == null ? null : new Date(value);
    }
    
    @Override
    public boolean isIdentityMapper() {
        return false;
    }
}