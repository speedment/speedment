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
package com.speedment.internal.gui.config;

import com.speedment.Speedment;
import com.speedment.config.Node;
import com.speedment.internal.newgui.property.BooleanPropertyItem;
import com.speedment.internal.newgui.property.StringPropertyItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractNodeProperty implements Node {
    
    private final Speedment speedment;
    private final StringProperty name;
    private final BooleanProperty enabled;
    private final BooleanProperty expanded;
    
    protected AbstractNodeProperty(Speedment speedment) {
        this.speedment = speedment;
        this.name      = new SimpleStringProperty();
        this.enabled   = new SimpleBooleanProperty();
        this.expanded  = new SimpleBooleanProperty();
    }
    
    protected AbstractNodeProperty(Speedment speedment, Node prototype) {
        requireNonNull(prototype);
        this.speedment = speedment;
        this.name      = new SimpleStringProperty(prototype.getName());
        this.enabled   = new SimpleBooleanProperty(prototype.isEnabled());
        this.expanded  = new SimpleBooleanProperty(prototype.isExpanded());
    }
    
    public Stream<PropertySheet.Item> getGuiVisibleProperties() {
        return Stream.concat(
            Stream.of(
                new BooleanPropertyItem(enabled, "General", "Enabled", "True if this node should be included in the code generation."), 
                new StringPropertyItem(name, "General", "Name", "The name of the persisted entity represented by this node.")
            ), guiVisibleProperties()
        );
    }
    
    protected abstract Stream<PropertySheet.Item> guiVisibleProperties();
    
    public final Speedment getSpeedment() {
        return speedment;
    }
    
    @Override
    public final void setName(String name) {
        this.name.set(name);
    }

    @Override
    public final String getName() {
        return name.get();
    }
    
    public final StringProperty nameProperty() {
        return name;
    }

    @Override
    public final void setEnabled(Boolean enabled) {
        this.enabled.set(enabled);
    }

    @Override
    public final Boolean isEnabled() {
        return enabled.get();
    }
    
    public final BooleanProperty enabledProperty() {
        return enabled;
    }

    @Override
    public final void setExpanded(Boolean expanded) {
        this.expanded.set(expanded);
    }

    @Override
    public final Boolean isExpanded() {
        return expanded.get();
    }
    
    public final BooleanProperty expandedProperty() {
        return expanded;
    }

    @Override
    public final boolean isImmutable() {
        return false;
    }
    
    protected IllegalArgumentException wrongParentClass(Class<?> parentType) {
        return new IllegalArgumentException(
            parentType.getSimpleName() + 
            " node can't be a parent to " + 
            getClass().getSimpleName() + "."
        );
    }
}