/**
 * 
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.tuple.internal.nullable.mapper;

import com.speedment.common.tuple.Tuple0;
import com.speedment.common.tuple.TupleMapper;
import com.speedment.common.tuple.TuplesOfNullables;
import com.speedment.common.tuple.nullable.Tuple0OfNullables;
import java.util.function.Function;

/**
 * An implementation class of a {@link TupleMapper } of degree 0
 * 
 * @param <T> Type of the original object for the mapper to use when creating a
 *            {@code Tuple }
 * 
 * @author Per Minborg
 */
public final class Tuple0OfNullablesMapperImpl<T> implements TupleMapper<T, Tuple0OfNullables> {
    
    public final static Tuple0OfNullablesMapperImpl<?> EMPTY_MAPPER = new Tuple0OfNullablesMapperImpl<>();
    
    /**
     * Constructs a {@link TupleMapper } that can create {@link Tuple0 }.
     */
    private Tuple0OfNullablesMapperImpl() {
        
    }
    
    @Override
    public Tuple0OfNullables apply(T t) {
        return TuplesOfNullables.ofNullables(
            
        );
    }
    
    @Override
    public int degree() {
        return 0;
    }
    
    @Override
    public Function<T, ?> get(int index) {
        switch(index){
            
            default : throw new IndexOutOfBoundsException();
        }
    }
}