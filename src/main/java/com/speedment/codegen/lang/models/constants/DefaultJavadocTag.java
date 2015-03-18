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
package com.speedment.codegen.lang.models.constants;

import com.speedment.codegen.lang.models.JavadocTag;
import com.speedment.codegen.lang.models.implementation.JavadocTagImpl.JavadocTagConst;

/**
 *
 * @author Emil Forslund
 */
public abstract class DefaultJavadocTag {
    private DefaultJavadocTag() {}
    
    public final static JavadocTag
		PARAM		= new JavadocTagConst("param"),
		AUTHOR		= new JavadocTagConst("author"),
		DEPRICATED	= new JavadocTagConst("depricated"),
		RETURN		= new JavadocTagConst("return"),
		SEE			= new JavadocTagConst("see"),
		THROWS		= new JavadocTagConst("throws"),
		SINCE		= new JavadocTagConst("since"),
		VERSION		= new JavadocTagConst("version");
}