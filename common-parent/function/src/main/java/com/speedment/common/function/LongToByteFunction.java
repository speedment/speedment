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
package com.speedment.common.function;

/**
 * Functional interface that corresponds to the method signature {@code byte
 * apply(long)}.
 * 
 * @author Emil Forslund
 * @since  1.0.4
 */
public interface LongToByteFunction {
    
    /**
     * Returns the {@code byte} value for the specified {@code long}. This
     * method should operate without side-effects.
     * 
     * @param value the input {@code long} value
     * @return      the resulting value
     */
    byte applyAsByte(long value);
}