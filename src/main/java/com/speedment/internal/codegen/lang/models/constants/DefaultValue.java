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
package com.speedment.internal.codegen.lang.models.constants;

import com.speedment.internal.codegen.lang.models.Value;
import com.speedment.internal.codegen.lang.models.values.NullValue;
import com.speedment.internal.codegen.lang.models.values.TextValue;
import static java.util.Objects.requireNonNull;

/**
 * Contains common default values used when generating java code.
 * 
 * @author Emil Forslund
 */
public final class DefaultValue {
    
    /**
     * The class should never be instantiated.
     */
    private DefaultValue() {}
    
    public final static Value<?>
		NULL         = new NullValue(),
		EMPTY_STRING = string("");
    
    /**
     * Creates a 'string' value with the specified content.
     * 
     * @param text  the inner text
     * @return      a value representing that string
     */
    public static final Value<String> string(String text) {
		return new TextValue(requireNonNull(text));
	}
}