package com.speedment.runtime.connector.sqlite;

import com.speedment.runtime.core.util.ProgressMeasure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Emil Forslund
 * @since  3.1.9
 */
public class MockProgress implements ProgressMeasure {

    private final List<Consumer<ProgressMeasure>> listeners;
    private double progress;
    private String action;

    public MockProgress() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void setProgress(double value) {
        progress = value;
        listeners.forEach(l -> l.accept(this));
    }

    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public void setCurrentAction(String action) {
        this.action = action;
        listeners.forEach(l -> l.accept(this));
    }

    @Override
    public String getCurrentAction() {
        return action;
    }

    @Override
    public ProgressMeasure addListener(Consumer<ProgressMeasure> listener) {
        listeners.add(listener);
        return this;
    }
}
