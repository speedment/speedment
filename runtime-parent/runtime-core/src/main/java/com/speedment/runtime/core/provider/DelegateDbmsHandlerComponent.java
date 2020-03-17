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
package com.speedment.runtime.core.provider;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.DbmsHandlerComponentImpl;

import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateDbmsHandlerComponent implements DbmsHandlerComponent {

    private final DbmsHandlerComponent inner;

    public DelegateDbmsHandlerComponent() {
        this.inner = new DbmsHandlerComponentImpl();
    }

    @Override
    public void install(DbmsType dbmsType) {
        inner.install(dbmsType);
    }

    @Override
    public Stream<DbmsType> supportedDbmsTypes() {
        return inner.supportedDbmsTypes();
    }

    @Override
    public Optional<DbmsType> findByName(String dbmsTypeName) {
        return inner.findByName(dbmsTypeName);
    }

}