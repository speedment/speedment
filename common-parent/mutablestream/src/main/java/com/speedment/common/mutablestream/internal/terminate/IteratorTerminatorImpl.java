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
import com.speedment.common.mutablestream.terminate.IteratorTerminator;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 *
 * @param <T>  the streamed type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class IteratorTerminatorImpl<T> 
extends AbstractTerminator<T, Stream<T>, Iterator<T>> 
implements IteratorTerminator<T> {

    public IteratorTerminatorImpl(HasNext<T, Stream<T>> previous, boolean parallel) {
        super(previous, parallel);
    }

    @Override
    public Iterator<T> execute() {
        try (final Stream<T> stream = buildPrevious()) {
            return stream.iterator();
        }
    }
}