package com.speedment.util.stream.builder;

import com.speedment.util.stream.builder.action.Action;
import java.util.LinkedList;
import java.util.function.Function;

/**
 *
 * @author pemi
 * @param <T>
 */
public abstract class NewTerminatingAction<T> implements Function<LinkedList<Action<?, ?>>, T> {
    
    private LinkedList<Action<?, ?>> actions;

    public NewTerminatingAction(LinkedList<Action<?, ?>> actions) {
        this.actions = actions;
    }
    
    protected LinkedList<Action<?, ?>> getActions() {
        return actions;
    }
}
