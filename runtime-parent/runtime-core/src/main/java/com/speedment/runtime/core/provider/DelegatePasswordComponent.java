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

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.internal.component.PasswordComponentImpl;

import java.util.Optional;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegatePasswordComponent implements PasswordComponent {

    private final PasswordComponent inner;

    public DelegatePasswordComponent() {
        this.inner = new PasswordComponentImpl();
    }

    @Override
    public void put(String dbmsName, char[] password) {
        inner.put(dbmsName, password);
    }

    @Override
    public void put(Dbms dbms, char[] password) {
        inner.put(dbms, password);
    }

    @Override
    public Optional<char[]> get(String dbmsName) {
        return inner.get(dbmsName);
    }

    @Override
    public Optional<char[]> get(Dbms dbms) {
        return inner.get(dbms);
    }
}
