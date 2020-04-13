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

import com.speedment.common.injector.MissingArgumentStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates that the method should be executed as part of the platform
 * initialization. Method parameters may have the annotation {@link WithState}
 * to indicate which state they must be in for this method to execute.
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Execute {

    /**
     * Returns the strategy to use if at least one of the arguments to the
     * annotated method can't be injected. The default behavior is to throw an
     * exception, but it is also possible to ignore the invocation.
     *
     * @return  strategy to use if argument is missing
     */
    MissingArgumentStrategy missingArgument() default MissingArgumentStrategy.THROW_EXCEPTION;

}