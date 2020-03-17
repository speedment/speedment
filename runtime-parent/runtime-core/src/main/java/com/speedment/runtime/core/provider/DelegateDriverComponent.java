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

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.db.DriverComponent;
import com.speedment.runtime.core.internal.db.DriverComponentImpl;

import java.sql.Driver;
import java.util.Optional;

import static com.speedment.common.injector.State.STARTED;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link DriverComponent}.
 */
public final class DelegateDriverComponent implements DriverComponent {

    private final DriverComponentImpl inner;

    public DelegateDriverComponent() {
        this.inner = new DriverComponentImpl();
    }

    @ExecuteBefore(STARTED)
    public void setInjector(Injector injector) {
        inner.setInjector(injector);
    }

    @Override
    public Optional<Driver> driver(String driverName) {
        return inner.driver(driverName);
    }
}
