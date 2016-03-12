/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.ui.config.trait;

import com.speedment.config.db.trait.*;
import com.speedment.config.db.Column;
import com.speedment.ui.config.DocumentProperty;
import java.util.Optional;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;

/**
 *
 * @author Emil Forslund
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