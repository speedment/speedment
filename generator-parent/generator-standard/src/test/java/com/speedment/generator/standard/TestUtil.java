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
package com.speedment.generator.standard;

import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.TranslatorBundle;
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.runtime.application.RuntimeBundle;
import com.speedment.runtime.application.provider.EmptyApplicationMetadata;
import com.speedment.runtime.connector.mysql.MySqlBundle;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.provider.*;

public final class TestUtil {

    private TestUtil() {}

    public static Injector injector()  {
        try {
            return Injector.builder()
                .withComponent(DelegateInfoComponent.class)
                .withComponent(StandardJavaLanguageNamer.class)
                .withBundle(TranslatorBundle.class)
                .withBundle(StandardTranslatorBundle.class)
                .withBundle(RuntimeBundle.class)
                .withComponent(EmptyApplicationMetadata.class)
                .withBundle(MySqlBundle.class)
                .build();
        } catch (InstantiationException e) {
            throw new SpeedmentException(e);
        }
    }
}