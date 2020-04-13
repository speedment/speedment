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
package com.speedment.tool.config.trait;

import com.speedment.runtime.config.trait.HasName;
import javafx.beans.property.BooleanProperty;

/**
 * Trait that defines if the {@link HasName#getName()}-field is protected from
 * edits in the tool or not. This should have no effect on the generated code.
 *
 * @author Emil Forslund
 * @since  3.0.20
 */
public interface HasNameProtectedProperty extends HasNameProperty {

    default BooleanProperty nameProtectedProperty() {
        return booleanPropertyOf(ConstantUtil.NAME_PROTECTED, this::isNameProtectedByDefault);
    }

    default boolean isNameProtected() {
        return nameProtectedProperty().get();
    }

    default boolean isNameProtectedByDefault() {
        return true;
    }

}
