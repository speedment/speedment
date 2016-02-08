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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import com.speedment.component.DocumentPropertyComponent;
import static com.speedment.component.DocumentPropertyComponent.concat;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.mutator.DocumentPropertyMutator;
import com.speedment.internal.ui.config.mutator.PrimaryKeyColumnPropertyMutator;
import com.speedment.internal.ui.config.trait.HasColumnProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.config.trait.HasOrdinalPositionProperty;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class PrimaryKeyColumnProperty extends AbstractChildDocumentProperty<Table, PrimaryKeyColumnProperty> 
    implements PrimaryKeyColumn, HasNameProperty, 
    HasOrdinalPositionProperty, HasColumnProperty {

    public PrimaryKeyColumnProperty(Table parent) {
        super(parent);
    }
    
    @Override
    public PrimaryKeyColumnPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return HasColumnProperty.super.getUiVisibleProperties(speedment);
    }
    
    @Override
    protected String[] keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.PRIMARY_KEY_COLUMNS, key);
    }
}