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
package com.speedment.runtime.connector.mariadb;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.connector.mariadb.provider.DelegateMariaDbDbmsType;
import com.speedment.runtime.connector.mariadb.provider.StandardMariaDbComponent;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since 3.0.0
 */
public class MariaDbBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            StandardMariaDbComponent.class,
            DelegateMariaDbDbmsType.class
        );
    }
}
