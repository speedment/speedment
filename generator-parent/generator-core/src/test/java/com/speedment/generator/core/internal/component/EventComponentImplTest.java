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
package com.speedment.generator.core.internal.component;

import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

final class EventComponentImplTest {

    private EventComponent instance;

    @BeforeEach
    void setup() {
        instance = new EventComponentImpl();
    }

    @Test
    void on() {
        final FooEventConsumer eventConsumer = new FooEventConsumer();
        instance.on(FooEvent.class, eventConsumer);
        final List<Event> events = Arrays.asList(new FooEvent(), new BarEvent());
        final List<Event> expected = events.stream().filter(FooEvent.class::isInstance).collect(Collectors.toList());
        expected.forEach(instance::notify);
        assertEquals(expected, eventConsumer.events());
    }

    @Test
    void onAny() {
        final EventConsumer eventConsumer = new EventConsumer();
        instance.onAny(eventConsumer);
        final List<Event> expected = Arrays.asList(new FooEvent(), new BarEvent());
        expected.forEach(instance::notify);
        assertEquals(expected, eventConsumer.events());
    }

    final static class FooEvent implements Event{}
    final static class BarEvent implements Event{}
    private static final class EventConsumer extends AbstractEventConsumer<Event> {};
    private static final class FooEventConsumer extends AbstractEventConsumer<FooEvent> {};
    private static final class BarEventConsumer extends AbstractEventConsumer<BarEvent> {};

    private static abstract class AbstractEventConsumer<T extends Event> implements Consumer<T> {

        final List<T> events = new ArrayList<>();

        @Override
        public void accept(T fooEvent) {
            events.add(fooEvent);
        }

        public List<T> events() {
            return events;
        }
    }
}