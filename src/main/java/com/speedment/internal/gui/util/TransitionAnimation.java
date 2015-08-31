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
package com.speedment.internal.gui.util;

import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.gui.util.FadeAnimation.fadeIn;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class TransitionAnimation {
    
    public static <T extends Pane> T enterFromRight(T node) {
        requireNonNull(node);
        return enterFromRight(node, ev -> {
            System.out.println("Final node position: " + node.getTranslateX() + " " + node.getTranslateY() + " " + node.getOpacity());
        });
    }

    public static <T extends Pane> T enterFromRight(T node, EventHandler<ActionEvent> onFinished) {
        requireNonNulls(node, onFinished);
        fadeIn(node);
        
        final Pane parent = (Pane) node.getParent();
        node.setTranslateY(parent.getHeight() - node.getPrefHeight() - 16);
        
        final KeyFrame kf0 = new KeyFrame(Duration.ZERO, new KeyValue(
            node.translateXProperty(), 
            parent.getWidth(),
            Interpolator.LINEAR
        ));
        
        final KeyFrame kf1 = new KeyFrame(
            Duration.seconds(1), 
            onFinished,
            new KeyValue(
                node.translateXProperty(), 
                parent.getWidth() - node.getPrefWidth() - 16,
                Interpolator.EASE_OUT
            )
        );
        
        final Timeline tl = new Timeline(kf0, kf1);
        tl.play();
        
        return node;
    }
    
    private TransitionAnimation() {instanceNotAllowed(getClass());}
}