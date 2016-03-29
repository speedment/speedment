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
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.config.db.Dbms;
import java.util.function.BiFunction;
import com.speedment.db.metadata.TypeInfoMetaData;

/**
 * The SqlTypeMapperComponent Component interface allows a mapping from a SQL
 * type to a Java type.
 *
 * @author pemi
 * @since 2.0
 */
@Api(version = "2.2")
public interface SqlTypeMapperComponent extends Component {

    @Override
    default Class<SqlTypeMapperComponent> getComponentClass() {
        return SqlTypeMapperComponent.class;
    }

    /**
     * Applies the mapping from Dbms and TypeInfo to a Java Class .
     *
     * @param dbms to apply
     * @param typeInfo to apply
     * @return the corresponding Java {@code Class}
     */
    Class<?> apply(Dbms dbms, TypeInfoMetaData typeInfo);

}
