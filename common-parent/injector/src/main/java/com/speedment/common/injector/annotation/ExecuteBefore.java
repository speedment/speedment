/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.State;
import java.lang.annotation.*;

/**
 * Annotates that the method should be executed as part of the platform
 * initialization. Method parameters may have the annotation {@link WithState}
 * to indicate which state they must be in for this method to execute.
 * <p>
 * This method must be executed before this component can be considered to be in
 * the specified state.
 *
 * @author  Emil Forslund
 * @since   1.0.0
 * 
 * @see  Execute
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecuteBefore {
    
    /**
     * The state before which this annotated method must be in when executed by
     * the injector.
     *
     * @return  the state
     */
    State value();

    /**
     * If the annotated method can not be executed due to one of the
     * dependencies (parameters) missing, should the injector throw an exception
     * or should it continue without invoking the method. If this is
     * {@code true}
     *
     * @return  {@code true} if an exception is thrown if the method can't be
     *          executed, and {@code false} if it will ignore it instead
     */
    boolean orThrow() default true;
}
