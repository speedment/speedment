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


import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.trait.HasColumn;
import com.speedment.tool.config.DocumentProperty;
import javafx.beans.binding.ObjectBinding;

import java.util.Optional;

import static javafx.beans.binding.Bindings.createObjectBinding;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */

public interface HasColumnProperty extends DocumentProperty, HasColumn, HasNameProperty {
    
    default ObjectBinding<Column> columnProperty() {
        return createObjectBinding(() ->
            HasColumn.super.findColumn().orElse(null), 
            nameProperty()
        );
    }

    @Override
    default Optional<? extends Column> findColumn() {
        return Optional.ofNullable(columnProperty().get());
    }
}