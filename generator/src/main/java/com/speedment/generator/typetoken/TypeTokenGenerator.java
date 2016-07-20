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
package com.speedment.generator.typetoken;

import com.speedment.common.codegen.model.Type;
import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.typetoken.TypeToken;

/**
 * Generates type tokens and codegen types from columns.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
@Api(version = "3.0")
@InjectorKey(TypeTokenGenerator.class)
public interface TypeTokenGenerator {

    /**
     * Returns the token describing the java type used to
     * store data from the specified column.
     * 
     * @param column  the column
     * @return        token describing its java type
     */
    TypeToken tokenOf(Column column);
    
    /**
     * Returns the a codegen {@link Type} for the specified
     * column in generated code.
     * 
     * @param column  the column
     * @return        the codegen type
     */
    Type typeOf(Column column);
}
