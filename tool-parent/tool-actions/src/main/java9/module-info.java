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
module com.speedment.tool.actions {
    exports com.speedment.tool.actions;
    exports com.speedment.tool.actions.menues;
    exports com.speedment.tool.actions.provider;

    requires com.speedment.common.invariant;
    requires com.speedment.common.mapstream;

    requires com.speedment.tool.config;

    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive com.speedment.common.injector;
    requires transitive com.speedment.runtime.config;
}
