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
module com.speedment.runtime.config {
    exports com.speedment.runtime.config;
    exports com.speedment.runtime.config.exception;
    exports com.speedment.runtime.config.identifier;
    exports com.speedment.runtime.config.identifier.trait;
    exports com.speedment.runtime.config.mutator;
    exports com.speedment.runtime.config.mutator.trait;
    exports com.speedment.runtime.config.parameter;
    exports com.speedment.runtime.config.provider;
    exports com.speedment.runtime.config.trait;
    exports com.speedment.runtime.config.util;

    requires com.speedment.common.invariant;
    requires transitive com.speedment.common.function;
}
