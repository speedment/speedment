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

import com.speedment.exception.SpeedmentException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static javafx.util.Duration.millis;

/**
 * Default implementation of the {@link Animation} interface.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
final class AnimationImpl<T extends Node> implements Animation<T> {
    
    private final T node;
    private final CompletableFuture<Void>[] futures;
    
    AnimationImpl(T node) {
        System.out.println("Call: new");
        this.node    = requireNonNull(node);
        this.futures = new CompletableFuture[0];
    }
    
    private AnimationImpl(T node, CompletableFuture<Void>[] futures) {
        this.node    = requireNonNull(node);
        this.futures = requireNonNull(futures);
    }

    @Override
    public Animation<T> hide() {
        System.out.println("Call: .hide()");
        node.setOpacity(0);
        return this;
    }

    @Override
    public Animation<T> show() {
        System.out.println("Call: .show()");
        node.setOpacity(1);
        return this;
    }

    @Override
    public Animation<T> fadeIn(int milliseconds) {
        System.out.println("Call: .fadeIn()");
        final FadeTransition ft = new FadeTransition(millis(milliseconds), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        return addTask(ft);
    }

    @Override
    public Animation<T> fadeOut(int milliseconds) {
        System.out.println("Call: .fadeOut()");
        final FadeTransition ft = new FadeTransition(millis(milliseconds), node);
        ft.setFromValue(1);
        ft.setToValue(0);
        return addTask(ft);
    }
    
    @Override
    public Animation<T> enterFromLeft(int milliseconds) {
        System.out.println("Call: .enterFromLeft()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double width = node.getBoundsInParent().getWidth();
        tt.setFromX(-width);
        tt.setToX(0);
        return addTask(tt);
    }

    @Override
    public Animation<T> enterFromUp(int milliseconds) {
        System.out.println("Call: .enterFromUp()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double height = node.getBoundsInParent().getHeight();
        tt.setFromY(-height);
        tt.setToY(0);
        return addTask(tt);
    }

    @Override
    public Animation<T> enterFromRight(int milliseconds) {
        System.out.println("Call: .enterFromRight()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double width = node.getBoundsInParent().getWidth();
        tt.setFromX(width);
        tt.setToX(0);
        return addTask(tt);
    }

    @Override
    public Animation<T> enterFromDown(int milliseconds) {
        System.out.println("Call: .enterFromDown()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double height = node.getBoundsInParent().getHeight();
        tt.setFromY(height);
        tt.setToY(0);
        return addTask(tt);
    }

    @Override
    public Animation<T> exitToLeft(int milliseconds) {
        System.out.println("Call: .exitToLeft()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double width = node.getBoundsInParent().getWidth();
        tt.setFromX(0);
        tt.setToX(-width);
        return addTask(tt);
    }

    @Override
    public Animation<T> exitToUp(int milliseconds) {
        System.out.println("Call: .exitToUp()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double height = node.getBoundsInParent().getHeight();
        tt.setFromY(0);
        tt.setToY(-height);
        return addTask(tt);
    }

    @Override
    public Animation<T> exitToRight(int milliseconds) {
        System.out.println("Call: .exitToRight()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double width = node.getBoundsInParent().getWidth();
        tt.setFromX(0);
        tt.setToX(width);
        return addTask(tt);
    }

    @Override
    public Animation<T> exitToDown(int milliseconds) {
        System.out.println("Call: .exitToDown()");
        final TranslateTransition tt = new TranslateTransition(millis(milliseconds), node);
        final double height = node.getBoundsInParent().getHeight();
        tt.setFromY(0);
        tt.setToY(height);
        return addTask(tt);
    }

    @Override
    public Animation<T> finish() {
        System.out.println("Call: .finish()");
        try {
            CompletableFuture.allOf(futures).get(3, TimeUnit.SECONDS);
        } catch (final ExecutionException | InterruptedException ex) {
            throw new SpeedmentException("Error running animation.", ex);
        } catch (final TimeoutException ex) {
            throw new SpeedmentException("Animation timed out.", ex);
        }
        
        return new AnimationImpl<>(node); // Create with empty array
    }

    @Override
    public Animation<T> then(Runnable action) {
        System.out.println("Call: .then()");
        final Animation<T> result = finish();
        action.run();
        return result;
    }

    @Override
    public Animation<T> remove() {
        System.out.println("Call: .remove()");
        childrenOf(node.getParent()).remove(node);
        return this;
    }

    @Override
    public Animation<T> appendTo(Node newParent) {
        System.out.println("Call: .appendTo()");
        childrenOf(newParent).add(this.node);
        return this;
    }
    
    private Animation<T> addTask(javafx.animation.Animation animation) {
        final CompletableFuture<Void> task = new CompletableFuture();
        animation.setOnFinished(ev -> task.complete(null));
        animation.play();
        return copyWithTask(task);
    }
    
    private Animation<T> copyWithTask(CompletableFuture<Void> task) {
        final CompletableFuture<Void>[] copy = Arrays.copyOf(futures, futures.length + 1);
        copy[futures.length] = task;
        return new AnimationImpl<>(node, copy);
    }
    
    private ObservableList<Node> childrenOf(Node node) {
        final Parent parent = node.getParent();
        
        if (parent == null) {
            throw new NullPointerException(
                "Can't delete node from 'null' parent."
            );
        }
        
        final Method getChildren;
        try {
            getChildren = parent.getClass().getMethod("getChildren");
        } catch (final NoSuchMethodException | SecurityException ex) {
            throw new SpeedmentException(
                "Could not find public method 'getChildren()' in class '" + 
                parent.getClass() + "'.", ex
            );
        }
        
        final ObservableList<Node> siblings;
        try {
            siblings = (ObservableList<Node>) getChildren.invoke(parent);
        } catch (final IllegalAccessException 
                     | IllegalArgumentException 
                     | InvocationTargetException 
                     | ClassCastException ex) {
            throw new SpeedmentException(
                "Error executing 'getChildren()' in class '" + 
                parent.getClass() + "'."
            );
        }
        
        return siblings;
    }
}