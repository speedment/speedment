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
package com.speedment.runtime.config.mapper.time;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import java.sql.Timestamp;

/**
 *
 * @author  Emil Forslund
 */
@Api(version = "3.0")
public class TimestampToLongMapper implements TypeMapper<Timestamp, Long> {

    @Override
    public Class<Long> getJavaType() {
        return Long.class;
    }

    @Override
    public Class<Timestamp> getDatabaseType() {
        return Timestamp.class;
    }

    @Override
    public Long toJavaType(Timestamp value) {
        return value == null ? null : value.getTime();
    }

    @Override
    public Timestamp toDatabaseType(Long value) {
        return value == null ? null : new Timestamp(value);
    }
    
    @Override
    public boolean isIdentityMapper() {
        return false;
    }
    
}