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
package com.speedment.codegen.model;

import com.speedment.util.Beans;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 * @param <T> the CodeModel type
 */
public abstract class AbstractCodeModel<T extends AbstractCodeModel<T>> implements CodeModel {

    @SuppressWarnings("unchecked")
    protected <P> T withSeveral(final P[] restOfParameters, final Consumer<P> consumer) {
        return Beans.withSeveral((T) this, restOfParameters, consumer);
    }

    @SuppressWarnings("unchecked")
    protected <P1, P2> T with(final P1 firstParameter, final P2 secondParameter, final BiConsumer<P1, P2> biConsumer) {
        return Beans.with((T) this, firstParameter, secondParameter, biConsumer);
    }

    @SuppressWarnings("unchecked")
    protected <P> T with(final P item, final Consumer<P> consumer) {
        return Beans.with((T) this, item, consumer);
    }

}
