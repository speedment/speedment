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
package com.speedment.runtime.join.provider;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.JoinComponent;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.builder.JoinBuilder1;
import com.speedment.runtime.join.internal.component.join.JoinComponentImpl;

public final class DelegateJoinComponent implements JoinComponent  {

    private final JoinComponent inner;

    public DelegateJoinComponent(JoinStreamSupplierComponent streamSupplier) {
        this.inner = new JoinComponentImpl(streamSupplier);
    }

    @Override
    public <T0> JoinBuilder1<T0> from(TableIdentifier<T0> firstTableIdentifier) {
        return inner.from(firstTableIdentifier);
    }
}
