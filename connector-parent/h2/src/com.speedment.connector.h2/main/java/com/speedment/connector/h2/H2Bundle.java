/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.connector.h2;

import com.speedment.common.injector.InjectBundle;
import com.speedment.connector.h2.internal.H2ComponentImpl;
import com.speedment.connector.h2.internal.H2DbmsType;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since 3.0.0
 */
public class H2Bundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(H2ComponentImpl.class, H2DbmsType.class);
    }
}
