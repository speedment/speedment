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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.EventComponent;
import com.speedment.event.DefaultEvent;
import com.speedment.event.Event;
import com.speedment.event.UIEvent;
import com.speedment.license.Software;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class EventComponentImpl extends InternalOpenSourceComponent implements EventComponent {
    
    private final Map<DefaultEvent, Set<Consumer<DefaultEvent>>> defaultEventListeners;
    private final Map<UIEvent, Set<Consumer<UIEvent>>> uiEventListeners;
    private final Map<Class<? extends Event>, Set<Consumer<Event>>> otherEventListeners;
    private final Set<Consumer<Event>> anyEventListeners;
    
    public EventComponentImpl(Speedment speedment) {
        super(speedment);
        
        final EnumMap<DefaultEvent, Set<Consumer<DefaultEvent>>> defaultListeners = 
            new EnumMap<>(DefaultEvent.class);
        
        for (final DefaultEvent ev : DefaultEvent.values()) {
            defaultListeners.put(ev, Collections.newSetFromMap(new ConcurrentHashMap<>()));
        }
        
        defaultEventListeners = Collections.unmodifiableMap(defaultListeners);
        
        final EnumMap<UIEvent, Set<Consumer<UIEvent>>> uiListeners = 
            new EnumMap<>(UIEvent.class);
        
        for (final UIEvent ev : UIEvent.values()) {
            uiListeners.put(ev, Collections.newSetFromMap(new ConcurrentHashMap<>()));
        }
        
        uiEventListeners = Collections.unmodifiableMap(uiListeners);
        
        otherEventListeners   = new ConcurrentHashMap<>();
        anyEventListeners     = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public void notify(Event event) {

        if (event instanceof DefaultEvent) {
            final DefaultEvent ev = (DefaultEvent) event;
            defaultListeners(ev).forEach(listener -> listener.accept(ev));
        } else if (event instanceof UIEvent) {
            final UIEvent ev = (UIEvent) event;
            uiListeners(ev).forEach(listener -> listener.accept(ev));
        } else {
            listeners(event.getClass()).forEach(listener -> listener.accept(event));
        }

        anyEventListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Event> void on(Class<E> event, Consumer<E> action) {
        listeners(event).add((Consumer<Event>) action);
    }

    @Override
    public void on(DefaultEvent event, Consumer<DefaultEvent> action) {
        defaultListeners(event).add(action);
    }
    
    @Override
    public void on(UIEvent event, Consumer<UIEvent> action) {
        uiListeners(event).add(action);
    }

    @Override
    public void onAny(Consumer<Event> action) {
        anyEventListeners.add(action);
    }
    
    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }
    
    private <E extends Event> Set<Consumer<Event>> listeners(Class<E> event) {
        final Set<Consumer<Event>> set = otherEventListeners
            .computeIfAbsent(event, ev -> Collections.newSetFromMap(new ConcurrentHashMap<>()));

        return set;
    }
    
    private Set<Consumer<DefaultEvent>> defaultListeners(DefaultEvent event) {
        return defaultEventListeners.get(event);
    }
    
    private Set<Consumer<UIEvent>> uiListeners(UIEvent event) {
        return uiEventListeners.get(event);
    }
}