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
package com.speedment.common.rest;

/**
 * An open interface for different types of options that can be passed to the
 * connector.
 *  
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface Option {
    
    /**
     * Enumeration of the different implementations of the {@link Option} 
     * interface.
     */
    enum Type { PARAM, HEADER }
    
    /**
     * The key of this option.
     * 
     * @return  the key
     */
    String getKey();
    
    /**
     * The value of this option.
     * 
     * @return  the value
     */
    String getValue();
    
    /**
     * What type of option this is.
     * 
     * @return  the option type
     */
    Type getType();
}