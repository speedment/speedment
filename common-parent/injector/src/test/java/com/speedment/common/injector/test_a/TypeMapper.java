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
package com.speedment.common.injector.test_a;

/**
 *
 * @param <DB_TYPE>    the type used in the JDBC driver
 * @param <JAVA_TYPE>  the type used to represent it in code
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface TypeMapper<DB_TYPE, JAVA_TYPE> {
    
    DB_TYPE toDatabase(JAVA_TYPE value);
    JAVA_TYPE toJava(DB_TYPE value);
    
}
