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
package com.speedment.runtime.field.collector;

import com.speedment.runtime.field.Field;
import java.util.stream.Collector;

/**
 *
 * @param <T>  the entity type to be collected
 * @param <A>  the intermediate accumulation type of the downstream collector
 * @param <R>  the collected result
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface FieldCollector<T, A, R> extends Collector<T, A, R> {

    /**
     * Returns the field that this collector is associated with.
     * 
     * @return  the field
     */
    Field<T> getField();
    
}