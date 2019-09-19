/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
module com.speedment.tool.propertyeditor {
    exports com.speedment.tool.propertyeditor;
    exports com.speedment.tool.propertyeditor.component;
    exports com.speedment.tool.propertyeditor.editor;
    exports com.speedment.tool.propertyeditor.item;

    requires com.speedment.common.injector;
    requires com.speedment.common.mapstream;

    requires com.speedment.runtime.core;
    requires com.speedment.runtime.config;
    requires com.speedment.runtime.typemapper;

    requires com.speedment.generator.translator;

    requires com.speedment.tool.config;
}
