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
package com.speedment.gui.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Emil Forslund
 */
public class LayoutAnimation implements ChangeListener<Number>, ListChangeListener<Node> {

	private final Map<Node, Transition> nodesInTransition;

	public LayoutAnimation() {
		this.nodesInTransition = new HashMap<>();
	}

	public void observe(ObservableList<Node> nodes) {
		nodes.forEach(n -> {
			this.observe(n);
		});
		nodes.addListener(this);
	}

	public void unobserve(ObservableList<Node> nodes) {
		nodes.removeListener(this);
	}

	public void observe(Node n) {
		n.layoutXProperty().addListener(this);
		n.layoutYProperty().addListener(this);
	}

	public void unobserve(Node n) {
		n.layoutXProperty().removeListener(this);
		n.layoutYProperty().removeListener(this);
	}

	@Override
	public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		final Double oldValueDouble = (Double) oldValue;
		final Double newValueDouble = (Double) newValue;
		final Double changeValueDouble = newValueDouble - oldValueDouble;
		final DoubleProperty doubleProperty = (DoubleProperty) ov;

		final Node node = (Node) doubleProperty.getBean();
		final TranslateTransition t;
		
		if (nodesInTransition.get(node) == null) {
			t = new TranslateTransition(Duration.millis(150), node);
		} else {
			t = (TranslateTransition) nodesInTransition.get(node);
		}

		if ("layoutX".equals(doubleProperty.getName())) {
			Double orig = node.getTranslateX();
			if (Double.compare(t.getFromX(), Double.NaN) == 0) {
				t.setFromX(orig - changeValueDouble);
				t.setToX(orig);
			}
		}
		
		if ("layoutY".equals(doubleProperty.getName())) {
			Double orig = node.getTranslateY();
			if (Double.compare(t.getFromY(), Double.NaN) == 0) {
				t.setFromY(orig - changeValueDouble);
				t.setToY(orig);
			}
		}
		
		t.play();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onChanged(Change<? extends Node> change) {
		while (change.next()) {
			if (change.wasAdded()) {
				change.getAddedSubList().forEach(n -> observe(n));
			} else if (change.wasRemoved()) {
				change.getRemoved().forEach(n -> unobserve(n));
			}
		}
	}
}
