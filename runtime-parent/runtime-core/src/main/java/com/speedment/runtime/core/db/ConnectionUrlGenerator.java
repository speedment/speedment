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
package com.speedment.runtime.core.db;

import com.speedment.runtime.config.Dbms;

/**
 * A functional interface that given a certain {@link Dbms} instance can produce
 * a connection URL.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@FunctionalInterface
public interface ConnectionUrlGenerator {
    
    /**
     * Returns the connection URL for the specified database information.
     * 
     * @param dbms  the dbms
     * @return      the connection url
     */
    String from(Dbms dbms);
}