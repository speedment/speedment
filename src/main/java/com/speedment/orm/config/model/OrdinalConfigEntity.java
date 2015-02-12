/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.orm.config.model;

/**
 *
 * @author pemi
 * @param <T> The type of the implementing class
 * @param <P> The type of the parent class.
 * @param <C> The type of the child class.
 */
public interface OrdinalConfigEntity<T extends ConfigEntity<T, P, C>, P extends ConfigEntity<?, ?, ?>, C extends ConfigEntity<?, ?, ?>> extends
        ConfigEntity<T, P, C> {

    int getOrdinalPosition();

    T setOrdinalPosition(int ordinalPosition);

}
