package com.speedment.internal.util;

import com.speedment.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 */
public class ProgressMeasurerImpl implements ProgressMeasure {

    private final List<Consumer<ProgressMeasure>> listeners;
    private double progress;
    private String currentAction;

    public ProgressMeasurerImpl() {
        progress = -1;
        currentAction = "";
        listeners = new CopyOnWriteArrayList<>();
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
    public String getCurrentAction(String action) {
        return currentAction;
    }

    @Override
    public ProgressMeasure addListener(Consumer<ProgressMeasure> listener) {
        listeners.add(listener);
        return this;
    }

    private void callListeners() {
        listeners.forEach(c -> c.accept(this));
    }

}
