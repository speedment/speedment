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
package com.speedment.internal.codegen.lang.interfaces;

import java.util.Optional;

/**
 * A trait for models that can have a comment.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasComment<T extends HasComment<T>> {
    
    /**
     * If a comment is attached to this model, return it. Else <code>empty</code>.
     * 
     * @return  the comment or empty
     */
	Optional<String> getComment();
    
    /**
     * Sets the comment of this model. The comment may have multiple rows.
     * 
     * @param comment  the comment
     * @return         a reference to this
     */
	T setComment(String comment);
}