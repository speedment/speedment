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
package com.speedment.common.mutablestream.internal.terminate;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.terminate.NoneMatchLongTerminator;
import static java.util.Objects.requireNonNull;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class NoneMatchLongTerminatorImpl
extends AbstractTerminator<Long, LongStream, Boolean> 
implements NoneMatchLongTerminator {

    private final LongPredicate predicate;

    public NoneMatchLongTerminatorImpl(HasNext<Long, LongStream> previous, boolean parallel, LongPredicate predicate) {
        super(previous, parallel);
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public LongPredicate getPredicate() {
        return predicate;
    }
    
    @Override
    public Boolean execute() {
        try (final LongStream stream = buildPrevious()) {
            return stream.noneMatch(predicate);
        }
    }
}