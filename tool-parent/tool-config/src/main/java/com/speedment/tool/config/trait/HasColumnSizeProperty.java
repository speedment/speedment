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

import com.speedment.runtime.config.trait.HasColumnSize;
import com.speedment.runtime.config.trait.HasColumnSizeUtil;
import com.speedment.tool.config.DocumentProperty;
import java.util.OptionalInt;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.2
 */
public interface HasColumnSizeProperty extends HasColumnSize, DocumentProperty {

    default IntegerProperty columnSizeProperty() {
        return integerPropertyOf(
            HasColumnSizeUtil.COLUMN_SIZE,
            () -> HasColumnSize.super.getColumnSize()
                .orElse(ConstantUtil.DEFAULT_COLUMN_SIZE)
        );
    }

    @Override
    default OptionalInt getColumnSize() {
        final Integer value = columnSizeProperty().getValue();
        if (value == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(value);
        }
    }
}