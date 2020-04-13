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

import java.lang.annotation.*;

/**
 * A configuration parameter that should be set automatically using
 * dependency injection. Every config parameter has a {@link #name()} and a 
 * {@link #value()}.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Inherited
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    
    /**
     * The name of this config parameter in the configuration file.
     * 
     * @return  the config parameter name
     */
    String name();
    
    /**
     * The default value that should be used if no value is configured.
     * 
     * @return  the default value
     */
    String value();
    
}