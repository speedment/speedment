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

import com.speedment.runtime.config.trait.HasEnumConstants;
import com.speedment.runtime.config.trait.HasEnumConstantsUtil;
import com.speedment.tool.config.DocumentProperty;
import javafx.beans.property.StringProperty;

import java.util.Optional;

/**
 * Specialization of the {@link HasEnumConstants}-trait for implementations that
 * also implement the {@link #enumConstantsProperty()}-method.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public interface HasEnumConstantsProperty
extends DocumentProperty, HasEnumConstants {

    /**
     * Observable property for the {@link #getEnumConstants()}-fields. The value
     * may be {@code null}.
     *
     * @return  the observable property for enum constants
     */
    default StringProperty enumConstantsProperty() {
        return stringPropertyOf(HasEnumConstantsUtil.ENUM_CONSTANTS,
            () -> HasEnumConstants.super.getEnumConstants().orElse(null)
        );
    }

    @Override
    default Optional<String> getEnumConstants() {
        return Optional.ofNullable(enumConstantsProperty().get());
    }
}
