package com.speedment.internal.util;

import com.speedment.util.*;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 */
public final class ProgressMeasurerImpl implements ProgressMeasure {

    private final Set<Consumer<ProgressMeasure>> listeners;
    private double progress;
    private String currentAction;

    public ProgressMeasurerImpl() {
        listeners     = newSetFromMap(new ConcurrentHashMap<>());
        progress      = ProgressMeasure.INDETERMINATE;
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