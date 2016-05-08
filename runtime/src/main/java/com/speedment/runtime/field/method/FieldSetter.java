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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.FieldTrait;
import java.util.function.UnaryOperator;

/**
 * Represents a set-operation with all the metadata contained.
 * 
 * @param <ENTITY>  entity type
 * @param <V>       column value type
 * 
 * @author  Emil Forslund
 * @author  Per Minborg
 * @since   2.2
 */
@Api(version = "2.3")
public interface FieldSetter<ENTITY, V> extends UnaryOperator<ENTITY> {

    /**
     * Returns the field that this setter sets.
     * 
     * @return  the field
     */
    FieldTrait getField();
    
    /**
     * Returns the value that this setter will set the field to when applied.
     * 
     * @return  the value
     */
    V getValue();
}