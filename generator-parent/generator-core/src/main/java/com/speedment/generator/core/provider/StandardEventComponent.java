package com.speedment.generator.core.provider;

import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.event.DefaultEvent;
import com.speedment.generator.core.event.Event;
import com.speedment.generator.core.internal.component.EventComponentImpl;

import java.util.function.Consumer;

public final class StandardEventComponent implements EventComponent {

    private final EventComponent inner;

    public StandardEventComponent() {
        this.inner = new EventComponentImpl();
    }

    @Override
    public <E extends Event> void on(Class<E> event, Consumer<E> action) {
        inner.on(event, action);
    }

    @Override
    public void on(DefaultEvent event, Consumer<DefaultEvent> action) {
        inner.on(event, action);
    }

    @Override
    public void onAny(Consumer<Event> action) {
        inner.onAny(action);
    }

    @Override
    public <E extends Event> void notify(E event) {
        inner.notify(event);
    }
}
