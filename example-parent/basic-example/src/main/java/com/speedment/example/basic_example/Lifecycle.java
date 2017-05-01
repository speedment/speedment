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
package com.speedment.example.basic_example;

import com.company.sakila.SakilaApplication;
import com.company.sakila.SakilaApplicationBuilder;
import com.company.sakila.db0.sakila.language.LanguageManager;
import com.speedment.runtime.core.ApplicationBuilder.LogType;

/**
 *
 * @author Per Minborg
 */
public class Lifecycle {

    public static void main(String[] args) {

        SakilaApplication app = new SakilaApplicationBuilder()
            .withPassword("sakila-password")
            .withLogging(LogType.STREAM)
            .build();

        LanguageManager languages = app.getOrThrow(LanguageManager.class);

        languages.stream()
            .forEachOrdered(System.out::println);

        app.stop();
    }

}
