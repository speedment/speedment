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
open module com.speedment.runtime.core {
    exports com.speedment.runtime.core;
    exports com.speedment.runtime.core.abstracts;
    exports com.speedment.runtime.core.component;
    exports com.speedment.runtime.core.component.connectionpool;
    exports com.speedment.runtime.core.component.resultset;
    exports com.speedment.runtime.core.component.sql;
    exports com.speedment.runtime.core.component.sql.override;
    exports com.speedment.runtime.core.component.sql.override.doubles;
    exports com.speedment.runtime.core.component.sql.override.ints;
    exports com.speedment.runtime.core.component.sql.override.longs;
    exports com.speedment.runtime.core.component.sql.override.reference;
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
    exports com.speedment.runtime.core.stream.java9;
    exports com.speedment.runtime.core.testsupport;
    exports com.speedment.runtime.core.util;

    requires com.speedment.common.invariant;
    requires com.speedment.common.logger;
    requires com.speedment.common.json;
    requires java.prefs; // InternalEmailUtil

    requires transitive com.speedment.common.function;
    requires transitive java.sql;
    requires transitive com.speedment.common.injector;
    requires transitive com.speedment.runtime.config;
    requires transitive com.speedment.runtime.typemapper;
    requires transitive com.speedment.runtime.field;
}
