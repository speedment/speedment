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
package com.speedment.internal.util.testing;

import com.speedment.Manager;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <ENTITY> type
 */
public interface MockManager<ENTITY> extends Manager<ENTITY> {

    /**
     * Sets the instance factory of this {@code MockManager}. The instance
     * factory is invoked each time a Managers {@link Manager#newInstance() }
     * method is called.
     *
     * @param factory the new instance factory to use
     * @return this instance
     */
    MockManager<ENTITY> setInstanceFactory(Supplier<ENTITY> factory);

    /**
     * Sets the native streamer of this {@code MockManager}.
     *
     * The native streamer is invoked each time a Managers {@link Manager#nativeStream() ()
     * } method is called.
     *
     * @param nativeStreamer the new native streamer supplier
     * @return this instance
     */
    MockManager<ENTITY> setNativeStreamer(Supplier<Stream<ENTITY>> nativeStreamer);

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
    MockManager<ENTITY> setPersister(Function<ENTITY, ENTITY> persister);

    /**
     * Sets the updater of this {@code MockManager}.
     *
     * The updater is invoked each time a Managers {@link Manager#update(java.lang.Object) ()
     * } method is called.
     *
     * @param updater the new streamer supplier
     * @return this instance
     */
    MockManager<ENTITY> setUpdater(Function<ENTITY, ENTITY> updater);

    /**
     * Sets the remover of this {@code MockManager}.
     *
     * The remover is invoked each time a Managers {@link Manager#remove(java.lang.Object) ()
     * } method is called.
     *
     * @param remover the new streamer supplier
     * @return this instance
     */
    MockManager<ENTITY> setRemover(Function<ENTITY, ENTITY> remover);

    static <ENTITY> MockManager<ENTITY> of(Manager<ENTITY> manager) {
        return new MockManagerImpl<>(manager);
    }


}
