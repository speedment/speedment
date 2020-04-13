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
package com.speedment.runtime.field.comparator;

import java.util.EnumMap;
import java.util.Map;

/**
 * Determines in if null values should be located before or after other values
 * in a list of results.
 *
 * @author Per Minborg
 * @since 2.2.0
 */
public enum NullOrder {

    NONE, FIRST, LAST;

    private static final Map<NullOrder, NullOrder> REVERSED_MAP = new EnumMap<>(NullOrder.class);

    static {
        REVERSED_MAP.put(NONE, NONE);
        REVERSED_MAP.put(FIRST, LAST);
        REVERSED_MAP.put(LAST, FIRST);
        if (REVERSED_MAP.size() != NullOrder.values().length) {
            throw new IllegalStateException("Missing mapping for NullOrder");
        }
    }

    public NullOrder reversed() {
        return REVERSED_MAP.get(this);
    }

}
