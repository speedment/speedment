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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.component.PasswordComponent;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Emil Forslund
 */
public final class PasswordComponentImpl extends Apache2AbstractComponent implements PasswordComponent {
    
    private final transient Map<String, String> passwords;

    public PasswordComponentImpl(Speedment speedment) {
        super(speedment);
        this.passwords = new ConcurrentHashMap<>();
    }

    @Override
    public Class<? extends Component> getComponentClass() {
        return PasswordComponent.class;
    }

    @Override
    public void put(String dbmsName, String password) {
        passwords.put(dbmsName, password);
    }

    @Override
    public Optional<String> get(String dbmsName) {
        return Optional.ofNullable(passwords.get(dbmsName));
    }    
}