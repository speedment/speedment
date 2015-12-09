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
package com.speedment.config.mapper;

import com.speedment.annotation.Api;

/**
 *
 * @author Emil Forslund
 * @param <DB_TYPE> the type as it is represented in the JDBC driver
 * @param <JAVA_TYPE> the type as it should be represented in generated code
 * @since 2.2
 */
@Api(version = "2.2")
public interface TypeMapper<DB_TYPE, JAVA_TYPE> {

    /**
     * Returns the label for this mapper that should appear to the end user.
     *
     * @return the label
     */
    default String getLabel() {
        return getDatabaseType()+" to "+getJavaType();
    }

    /**
     * Returns the type as it should be represented in generated code.
     *
     * @return the type
     */
    Class<JAVA_TYPE> getJavaType();

    /**
     * Returns the type as it is represented in the JDBC driver.
     *
     * @return the type
     */
    Class<DB_TYPE> getDatabaseType();

    /**
     * Converts a value from the database domain to the java domain.
     *
     * @param value the value to convert
     * @return the converted value
     */
    JAVA_TYPE toJavaType(DB_TYPE value);

    /**
     * Converts a value from the java domain to the database domain.
     *
     * @param value the value to convert
     * @return the converted value
     */
    DB_TYPE toDatabaseType(JAVA_TYPE value);

    /*
    * Returns if this is an identity mapper. An identity mapper
    * is a one-to-one mapper between the JDBC and the generated code type. For example,
    * A String to String is an indentity mapper.
     */
    boolean isIdentityMapper();

}
