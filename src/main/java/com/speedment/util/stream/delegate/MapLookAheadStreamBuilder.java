/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.util.stream.delegate;

import com.speedment.util.stream.delegate.action.Action;
import com.speedment.util.stream.delegate.action.MapAction;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class MapLookAheadStreamBuilder<T, R> extends LookAheadStreamBuilder<R> {

    private final MapAction<T, R> mapAction;
        //private final Function<? super T, ? extends R> mapper;

    //public MapLookAheadStream(final LookAheadStream<T> parent, final Function<? super T, ? extends R> mapper) {
    public MapLookAheadStreamBuilder(final LookAheadStreamBuilder<T> parent, final MapAction mapAction) {
        super(parent);
        this.mapAction = mapAction;
    }

    @Override
    protected boolean isParallelByDefault() {
        return getParent().get().isParallel();
    }

    @Override
    protected Stream<R> create() {
        // Inspect and then...
        final Stream<T> parentStream = getParent().get().create();
        return parentStream.map(mapAction.getMapper());
    }

    @Override
    protected Stream<Action> actionsGlobal() {
        return Stream.concat(Stream.concat(getParent().get().actionsGlobal(), Stream.of(mapAction)), actionsLocal());
    }

}
