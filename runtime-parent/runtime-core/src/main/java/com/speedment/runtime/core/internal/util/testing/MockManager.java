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
package com.speedment.runtime.core.internal.util.testing;

import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public interface MockManager<ENTITY> extends Manager<ENTITY> {

    /**
     * Sets the streamer of this {@code MockManager}.
     *
     * The streamer is invoked each time a Managers {@link Manager#stream() () ()
     * } method is called.
     *
     * @param streamer the new streamer supplier
     * @return this instance
     */
    MockManager<ENTITY> setStreamer(Supplier<Stream<ENTITY>> streamer);

    /**
     * Sets the streamer of this {@code MockManager}.
     *
     * The streamer is invoked each time a Managers {@link Manager#persist(java.lang.Object) () ()
     * } method is called.
     *
     * @param persister the new streamer supplier
     * @return this instance
     */
    MockManager<ENTITY> setPersister(Persister<ENTITY> persister);

    /**
     * Sets the updater of this {@code MockManager}.
     *
     * The updater is invoked each time a Managers {@link Manager#update(java.lang.Object) ()
     * } method is called.
     *
     * @param updater the new streamer supplier
     * @return this instance
     */
    MockManager<ENTITY> setUpdater(Updater<ENTITY> updater);

    /**
     * Sets the remover of this {@code MockManager}.
     *
     * The remover is invoked each time a Managers {@link Manager#remove(java.lang.Object) ()
     * } method is called.
     *
     * @param remover the new streamer supplier
     * @return this instance
     */
    MockManager<ENTITY> setRemover(Remover<ENTITY> remover);

    /**
     * Wraps the specified manager in a new {@link MockManager}.
     * 
     * @param <ENTITY>  the entity type
     * @param manager   the manager to wrap
     * @return          the new {@code MockManager}
     */
    static <ENTITY> MockManager<ENTITY> of(Manager<ENTITY> manager) {
        return new MockManagerImpl<>(manager);
    }
}