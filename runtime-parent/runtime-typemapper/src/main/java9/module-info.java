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
module com.speedment.runtime.typemapper {
    exports com.speedment.runtime.typemapper;
    exports com.speedment.runtime.typemapper.bigdecimal;
    exports com.speedment.runtime.typemapper.bytes;
    exports com.speedment.runtime.typemapper.doubles;
    exports com.speedment.runtime.typemapper.exception;
    exports com.speedment.runtime.typemapper.integer;
    exports com.speedment.runtime.typemapper.largeobject;
    exports com.speedment.runtime.typemapper.longs;
    exports com.speedment.runtime.typemapper.other;
    exports com.speedment.runtime.typemapper.primitive;
    exports com.speedment.runtime.typemapper.shorts;
    exports com.speedment.runtime.typemapper.string;
    exports com.speedment.runtime.typemapper.time;

    requires transitive java.sql;
    requires transitive com.speedment.runtime.config;
    requires transitive com.speedment.common.injector;
}
