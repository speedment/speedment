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
package com.speedment.internal.gui.properties;

import com.speedment.Speedment;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

/**
 *
 * @author Emil Forslund
 * @param <V> The type of the value column.
 */
public abstract class TableProperty<V> implements Comparable<TableProperty<V>> {
	
    private final Speedment speedment;
	private final StringProperty name;
	
	protected TableProperty(Speedment speedment, String name) {
        this.speedment = requireNonNull(speedment);
		this.name      = new SimpleStringProperty(requireNonNull(name));
	}
	
	public final StringProperty nameProperty() {
		return name;
	}

	public abstract Property<V> valueProperty();
	public abstract Node getValueGraphic();
    
    protected Speedment getSpeedment() {
        return speedment;
    }

    @Override
    public int compareTo(TableProperty<V> that) {
        // that nullable
        return Optional.ofNullable(that)
            .map(TableProperty::nameProperty)
            .map(StringProperty::getValue)
            .map(name.getValue()::compareTo)
            .orElse(0);
    }

	@Override
	public int hashCode() {
		if (name == null) {
			return 0;
		} else {
			return Objects.hashCode(name.get());
		}
	}

	@Override
	public boolean equals(Object obj) {
		return Optional.ofNullable(obj)
			.filter(o -> TableProperty.class.isAssignableFrom(o.getClass()))
			.map(o -> (TableProperty<?>) o)
			.filter(tp -> name != null || name == tp.name)
			.filter(tp -> name.get().equals(tp.name.get()))
			.isPresent();
	}
}