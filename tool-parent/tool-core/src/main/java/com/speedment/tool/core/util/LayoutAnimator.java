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
package com.speedment.tool.core.util;


import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * Animates an object when its position is changed. For instance, when
 * additional items are added to a Region, and the layout has changed, then the
 * layout animator makes the transition by sliding each item into its final
 * place.
 * 
 * @author  jewelsea
 * @since   2.3.0
 * @see     <a href="https://gist.github.com/jewelsea/5683558">Original Gist</a>
 */
public final class LayoutAnimator implements ChangeListener<Number>, ListChangeListener<Node> {

    private final Map<Node, TranslateTransition> nodeXTransitions = new HashMap<>();
    private final Map<Node, TranslateTransition> nodeYTransitions = new HashMap<>();

    /**
     * Animates all the children of a Region.
     * <code>
     *   VBox myVbox = new VBox();
     *   LayoutAnimator animator = new LayoutAnimator();
     *   animator.observe(myVbox.getChildren());
     * </code>
     *
     * @param nodes  the nodes to observe
     */
    public void observe(ObservableList<Node> nodes) {
        nodes.forEach(this::observe);
        nodes.addListener(this);
    }

    public void unobserve(ObservableList<Node> nodes) {
        nodes.removeListener(this);
    }

    private void observe(Node n) {
        n.layoutXProperty().addListener(this);
        n.layoutYProperty().addListener(this);
    }

    private void unobserve(Node n) {
        n.layoutXProperty().removeListener(this);
        n.layoutYProperty().removeListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
        final double delta = newValue.doubleValue() - oldValue.doubleValue();
        final DoubleProperty doubleProperty = (DoubleProperty) ov;
        final Node node = (Node) doubleProperty.getBean();

        TranslateTransition t;
        if (doubleProperty.getName().equals("layoutX")) {
            t = nodeXTransitions.get(node);
            if (t == null) {
                t = new TranslateTransition(Duration.millis(150), node);
                t.setToX(0);
                nodeXTransitions.put(node, t);
            }
            t.setFromX(node.getTranslateX() - delta);
            node.setTranslateX(node.getTranslateX() - delta);
        } else { //layout Y
            t = nodeYTransitions.get(node);
            if (t == null) {
                t = new TranslateTransition(Duration.millis(150), node);
                t.setToY(0);
                nodeYTransitions.put(node, t);
            }
            t.setFromY(node.getTranslateY() - delta);
            node.setTranslateY(node.getTranslateY() - delta);
        }

        t.playFromStart();
    }

    @Override
    public void onChanged(Change<? extends Node> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                change.getAddedSubList().forEach(this::observe);
            } else if (change.wasRemoved()) {
                change.getRemoved().forEach(this::unobserve);
            }
        }
    }
}
