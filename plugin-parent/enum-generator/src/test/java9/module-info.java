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
open module com.speedment.plugins.enums {
    exports com.speedment.plugins.enums;

    requires com.speedment.common.injector;
    requires com.speedment.common.codegen;
    requires com.speedment.common.logger;
    requires com.speedment.common.singletonstream;

    requires com.speedment.runtime.config;
    requires com.speedment.runtime.typemapper;

    requires com.speedment.generator.core;
    requires com.speedment.generator.standard;
    requires com.speedment.generator.translator;

    requires com.speedment.tool.core;
    requires com.speedment.tool.config;
    requires com.speedment.tool.propertyeditor;

    // Test requirements
    requires com.speedment.common.json;

}
