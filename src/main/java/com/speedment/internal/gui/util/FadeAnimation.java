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
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class FadeAnimation {

    public final static double SLOW = 1000, NORMAL = 500, FAST = 200;

    public static <T extends Node> T fadeOut(T node) {
        requireNonNull(node);
        return fadeOut(node, null);
    }

    public static <T extends Node> T fadeIn(T node) {
        requireNonNull(node);
        return fadeIn(node, null);
    }

    public static <T extends Node> T fadeOut(T node, EventHandler<ActionEvent> onFinished) {
        requireNonNull(node);
        //onFinished nullable
        return fade(node, 1.0, 0.0, FAST, onFinished);
    }

    public static <T extends Node> T fadeIn(T node, EventHandler<ActionEvent> onFinished) {
        requireNonNull(node);
        //onFinished nullable
        return fade(node, 0.0, 1.0, FAST, onFinished);
    }

    private static <T extends Node> T fade(T node, double from, double to, double duration, EventHandler<ActionEvent> onFinished) {
        requireNonNull(node);
        //onFinished nullable
        node.setOpacity(from);
        final FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(from);
        ft.setToValue(to);
        ft.play();
        ft.setOnFinished(onFinished);
        return node;
    }

    private FadeAnimation() {
        instanceNotAllowed(getClass());
    }
}
