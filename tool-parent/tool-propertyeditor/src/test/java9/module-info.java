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
open module com.speedment.tool.propertyeditor {
    exports com.speedment.tool.propertyeditor;
    exports com.speedment.tool.propertyeditor.component;
    exports com.speedment.tool.propertyeditor.editor;
    exports com.speedment.tool.propertyeditor.item;
    exports com.speedment.tool.propertyeditor.provider;

    requires com.speedment.runtime.typemapper;
    requires com.speedment.common.codegen;
    requires com.speedment.generator.translator;

    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive com.speedment.common.injector;
    requires transitive com.speedment.common.mapstream;
    requires transitive com.speedment.runtime.core;
    requires transitive com.speedment.runtime.config;
    requires transitive com.speedment.tool.config;
}
