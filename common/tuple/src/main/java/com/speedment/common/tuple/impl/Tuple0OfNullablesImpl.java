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
package com.speedment.common.tuple.impl;

import com.speedment.common.tuple.Tuple0OfNullables;

/**
 *
 * @author pemi
 */
public final class Tuple0OfNullablesImpl extends AbstractTupleOfNullables implements Tuple0OfNullables {

    public static final Tuple0OfNullables EMPTY_TUPLE = new Tuple0OfNullablesImpl();

    @SuppressWarnings("rawtypes")
    private Tuple0OfNullablesImpl() {
        super(Tuple0OfNullablesImpl.class);
    }
}