/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.core.component.PasswordComponent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class PasswordComponentImpl implements PasswordComponent {

    private final transient Map<String, char[]> passwords;

    public PasswordComponentImpl() {
        this.passwords = new ConcurrentHashMap<>();
    }

    @Override
    public void put(String dbmsName, char[] password) {
        passwords.put(dbmsName, password);
    }

    @Override
    public Optional<char[]> get(String dbmsName) {
        return Optional.ofNullable(passwords.get(dbmsName));
    }
}