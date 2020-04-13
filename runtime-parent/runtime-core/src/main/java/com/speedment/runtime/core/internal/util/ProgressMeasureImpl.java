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
package com.speedment.runtime.core.internal.util;

import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.runtime.core.util.ProgressMeasureUtil;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class ProgressMeasureImpl implements ProgressMeasure {

    private final Set<Consumer<ProgressMeasure>> listeners;
    private double progress;
    private String currentAction;

    public ProgressMeasureImpl() {
        listeners     = newSetFromMap(new ConcurrentHashMap<>());
        progress      = ProgressMeasureUtil.INDETERMINATE;
        currentAction = "";
    }

    @Override
    public void setProgress(double value) {
        this.progress = value;
        callListeners();
    }

    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public void setCurrentAction(String action) {
        this.currentAction = action;
        callListeners();
    }

    @Override
    public String getCurrentAction() {
        return currentAction;
    }

    @Override
    public ProgressMeasure addListener(Consumer<ProgressMeasure> listener) {
        requireNonNull(listener);
        listeners.add(listener);
        return this;
    }

    private void callListeners() {
        listeners.forEach(c -> c.accept(this));
    }
}