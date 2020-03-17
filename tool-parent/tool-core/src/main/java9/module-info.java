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
module com.speedment.tool.core {
    exports com.speedment.tool.core;
    exports com.speedment.tool.core.brand;
    exports com.speedment.tool.core.component;
    exports com.speedment.tool.core.event;
    exports com.speedment.tool.core.exception;
    exports com.speedment.tool.core.menubar;
    exports com.speedment.tool.core.notification;
    exports com.speedment.tool.core.provider;
    exports com.speedment.tool.core.resource;
    exports com.speedment.tool.core.rule;
    exports com.speedment.tool.core.toolbar;
    exports com.speedment.tool.core.util;

    requires com.speedment.common.rest;
    requires com.speedment.common.json;
    requires com.speedment.common.mapstream;
    requires com.speedment.common.logger;

    requires com.speedment.runtime.config;
    requires com.speedment.runtime.typemapper;
    requires com.speedment.runtime.application;

    requires com.speedment.tool.actions;

    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive com.speedment.common.injector;
    requires transitive com.speedment.runtime.core;
    requires transitive com.speedment.generator.core;
    requires transitive com.speedment.generator.translator;
    requires transitive com.speedment.tool.config;
    requires transitive com.speedment.tool.propertyeditor;
}
