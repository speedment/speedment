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
package com.speedment.common.injector.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a secondary constructor that should be used when the dependency
 * injector creates instances if and only if all the classes specified as its
 * value lacks implementations in the context of the injector. It is not enough
 * that the types listed have not been created yet, or that they have not yet
 * reached a particular state. They must be completely unspecified for the
 * annotated constructor to be used.
 * <p>
 * Note that this annotation is not a replacement for the {@link Inject}
 * annotation, but rather a complement.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
@Inherited
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyIfMissing {

    /**
     * Only if <em>all</em> of the classes given by this field are missing
     * should the annotated constructor be used to create an instance of the
     * class.
     *
     * @return  all the classes that must be missing for the annotated
     *          constructor to be invoked
     */
    Class<?>[] value();

}
