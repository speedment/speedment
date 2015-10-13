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
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.config.mapper.TypeMapper;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
@Api(version="2.2")
public interface TypeMapperComponent extends Component {
    
    /**
     * Installs the specified type mapper in this component.
     * 
     * @param <DB_TYPE>    the database type
     * @param <JAVA_TYPE>  the java type
     * @param typeMapper   the mapper to install
     */
    <DB_TYPE, JAVA_TYPE> void install(TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper);
    
    /**
     * Streams over all the type mappers installed in this component.
     * 
     * @param <DB_TYPE>    the database type
     * @param <JAVA_TYPE>  the java type
     * @return             all mappers
     */
    <DB_TYPE, JAVA_TYPE> Stream<TypeMapper<DB_TYPE, JAVA_TYPE>> stream();
}