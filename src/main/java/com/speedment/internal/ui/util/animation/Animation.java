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
package com.speedment.internal.ui.util.animation;

import javafx.scene.Node;

/**
 * A utility class for fading in and out various JavaFX components.
 * <pre>
 *      Animation.of(node)
 *          .fadeOut()
 *          .moveLeft()
 *          .finish()
 *          .remove();
 * </pre>
 *
 * @author Emil Forslund
 * @since 2.3
 */
public interface Animation<T extends Node> {

    static int FAST = 100, NORMAL = 250, SLOW = 500;
    
    static <T extends Node> Animation<T> of(T node) {
        return new AnimationImpl<>(node);
    }
    
    // Show and hide
    Animation<T> hide();
    
    Animation<T> show();

    // Fade transition
    default Animation<T> fadeIn() {
        return fadeIn(NORMAL);
    }

    Animation<T> fadeIn(int milliseconds);

    default Animation<T> fadeOut() {
        return fadeOut(NORMAL);
    }

    Animation<T> fadeOut(int milliseconds);
    
    // Enter transition
    default Animation<T> enterFromLeft() {
        return Animation.this.enterFromLeft(NORMAL);
    }

    Animation<T> enterFromLeft(int milliseconds);

    default Animation<T> enterFromUp() {
        return Animation.this.enterFromUp(NORMAL);
    }

    Animation<T> enterFromUp(int milliseconds);

    default Animation<T> enterFromRight() {
        return Animation.this.enterFromRight(NORMAL);
    }

    Animation<T> enterFromRight(int milliseconds);

    default Animation<T> enterFromDown() {
        return Animation.this.enterFromDown(NORMAL);
    }

    Animation<T> enterFromDown(int milliseconds);
    
    // Exit transition
    default Animation<T> exitToLeft() {
        return Animation.this.exitToLeft(NORMAL);
    }

    Animation<T> exitToLeft(int milliseconds);

    default Animation<T> exitToUp() {
        return Animation.this.exitToUp(NORMAL);
    }

    Animation<T> exitToUp(int milliseconds);

    default Animation<T> exitToRight() {
        return Animation.this.exitToRight(NORMAL);
    }

    Animation<T> exitToRight(int milliseconds);

    default Animation<T> exitToDown() {
        return Animation.this.exitToDown(NORMAL);
    }

    Animation<T> exitToDown(int milliseconds);

    // Synchronization
    Animation<T> finish();
    
    Animation<T> then(Runnable action);
    
    // Tree manipulation
    Animation<T> appendTo(Node newParent);

    Animation<T> remove();
    
    default Animation<T> removeWhenFinished() {
        return finish().remove();
    }
}