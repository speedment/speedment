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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.EventComponent;
import com.speedment.event.DefaultEvent;
import com.speedment.event.Event;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 *
 * @author Emil Forslund
 */
public final class EventComponentImpl extends Apache2AbstractComponent implements EventComponent {
    
    private final Map<DefaultEvent, Set<Consumer<DefaultEvent>>> defaultEventListeners;
    private final Map<String, Set<Consumer<? extends Event>>> otherEventListeners;
    private final Set<Consumer<Event>> anyEventListeners;
    
    public EventComponentImpl(Speedment speedment) {
        super(speedment);
        
        final EnumMap<DefaultEvent, Set<Consumer<DefaultEvent>>> listeners = new EnumMap<>(DefaultEvent.class);
        for (final DefaultEvent ev : DefaultEvent.values()) {
            listeners.put(ev, Collections.newSetFromMap(new ConcurrentHashMap<>()));
        }
        
        defaultEventListeners = Collections.unmodifiableMap(listeners);
        otherEventListeners   = new ConcurrentHashMap<>();
        anyEventListeners     = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public void notify(Event event) {
        final Consumer<Consumer<Event>> notifier = listener -> listener.accept(event);
        listeners(event).forEach(notifier);
        anyEventListeners.forEach(notifier);
    }

    @Override
    public <E extends Event> void on(E event, Consumer<E> action) {
        listeners(event).add(action);
    }

    @Override
    public void onAny(Consumer<Event> action) {
        anyEventListeners.add(action);
    }
    
    private <E extends Event> Set<Consumer<E>> listeners(E event) {
        if (event instanceof DefaultEvent) {
            @SuppressWarnings("unchecked")
            final DefaultEvent key = (DefaultEvent) event;
            
            @SuppressWarnings("unchecked")
            final Set<Consumer<E>> set = (Set<Consumer<E>>) (Object) defaultEventListeners.get(key);
            
            return set;
        } else {
            @SuppressWarnings("unchecked")
            final Set<Consumer<E>> set = (Set<Consumer<E>>) (Object) otherEventListeners
                .computeIfAbsent(event.name(), ev -> Collections.newSetFromMap(new ConcurrentHashMap<>()));
            
            return set;
        }
    }
}