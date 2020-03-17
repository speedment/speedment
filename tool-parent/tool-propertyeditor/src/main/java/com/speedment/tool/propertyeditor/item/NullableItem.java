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
package com.speedment.tool.propertyeditor.item;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasNullable.ImplementAs;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import static java.util.Objects.requireNonNull;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class NullableItem extends AbstractLabelTooltipItem {
    
    private static final String NULLABLE_TITLE = "Is Nullable";
    private static final String NULLABLE_TOOLTIP = "If this node can hold 'null'-values or not.";
    private static final String IMPLEMENTATION_TITLE = "If this node can hold 'null'-values or not.";
    private static final String IMPLEMENTATION_TOOLTIP = "How the nullable column should be implemented, either by " +
        "using an Optional or by using a wrapper class that can have " +
        "a null value.";


    private final Document document;
    private final BooleanProperty nullable;
    private final ObjectProperty<ImplementAs> implementation;
    
    public NullableItem(Document document, BooleanProperty nullable, ObjectProperty<ImplementAs> implementation) {
        super(
            NULLABLE_TITLE, 
            NULLABLE_TOOLTIP, 
            NO_DECORATOR
        );

        this.document       = requireNonNull(document);
        this.nullable       = requireNonNull(nullable);
        this.implementation = requireNonNull(implementation);
    }

    @Override
    protected Node createUndecoratedEditor() {
        
        final CheckBox cbNull               = new CheckBox();
        final Node wrappedCb                = ItemUtil.lockDecorator(cbNull, document, ItemUtil.DATABASE_RELATION_TOOLTIP);
        final Label label                   = new Label(IMPLEMENTATION_TITLE);
        final ChoiceBox<ImplementAs> cbImpl = new ChoiceBox<>(
            observableArrayList(ImplementAs.values())
        );
        
        cbImpl.setTooltip(new Tooltip(IMPLEMENTATION_TOOLTIP));
        
        final HBox right = new HBox(label, cbImpl);
        final HBox left = new HBox(wrappedCb, right);
        
        left.setSpacing(16);
        right.setSpacing(8);
        right.setAlignment(Pos.CENTER);
        
        cbNull.selectedProperty().bindBidirectional(nullable);
        cbImpl.valueProperty().bindBidirectional(implementation);
        
        right.visibleProperty().bind(nullable);
        right.disableProperty().bind(nullable.not());
        
        return left;
    }
    
}
