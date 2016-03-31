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
import com.speedment.config.db.Index;
import com.speedment.config.db.Table;
import com.speedment.config.db.mutator.IndexMutator;
import com.speedment.internal.ui.config.mutator.DocumentPropertyMutator;
import com.speedment.internal.ui.config.mutator.IndexPropertyMutator;
import com.speedment.ui.config.trait.HasEnabledProperty;
import com.speedment.ui.config.trait.HasExpandedProperty;
import com.speedment.ui.config.trait.HasNameProperty;
import com.speedment.ui.config.db.BooleanPropertyItem;
import static com.speedment.internal.util.ImmutableListUtil.*;
import java.util.List;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class IndexProperty extends AbstractChildDocumentProperty<Table, IndexProperty>
    implements Index, HasEnabledProperty, HasExpandedProperty, HasNameProperty {

    public IndexProperty(Table parent) {
        super(parent);
    }

    public BooleanProperty uniqueProperty() {
        return booleanPropertyOf(UNIQUE, Index.super::isUnique);
    }

    @Override
    public boolean isUnique() {
        return uniqueProperty().get();
    }

    @Override
    public Stream<IndexColumnProperty> indexColumns() {
        return indexColumnsProperty().stream();
    }

    public ObservableList<IndexColumnProperty> indexColumnsProperty() {
        return observableListOf(INDEX_COLUMNS);
    }

    @Override
    public boolean isExpandedByDefault() {
        return false;
    }

    @Override
    public IndexPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            Stream.of(
                new BooleanPropertyItem(
                    uniqueProperty(),
                    "Is Unique",
                    "True if elements in this index are unique."
                )
            )
        ).flatMap(s -> s);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.INDEXES, key);
    }
}
