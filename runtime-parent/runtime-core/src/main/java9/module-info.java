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
module com.speedment.runtime.core {
    exports com.speedment.runtime.core;
    exports com.speedment.runtime.core.abstracts;
    exports com.speedment.runtime.core.component;
    exports com.speedment.runtime.core.component.connectionpool;
    exports com.speedment.runtime.core.component.resultset;
    exports com.speedment.runtime.core.component.sql;
    exports com.speedment.runtime.core.component.transaction;
    exports com.speedment.runtime.core.db;
    exports com.speedment.runtime.core.db.metadata;
    exports com.speedment.runtime.core.exception;
    exports com.speedment.runtime.core.manager;
    exports com.speedment.runtime.core.manager.sql;
    exports com.speedment.runtime.core.provider;
    exports com.speedment.runtime.core.stream;
    exports com.speedment.runtime.core.stream.action;
    exports com.speedment.runtime.core.stream.parallel;
    exports com.speedment.runtime.core.support;
    exports com.speedment.runtime.core.util;

    requires com.speedment.common.invariant;
    requires com.speedment.common.function;
    requires com.speedment.common.mapstream;
    requires com.speedment.common.logger;
    requires com.speedment.common.json;
    requires com.speedment.common.tuple;

    requires transitive java.sql;
    requires transitive com.speedment.common.injector;
    requires transitive com.speedment.runtime.config;
    requires transitive com.speedment.runtime.typemapper;
    requires transitive com.speedment.runtime.field;
}
