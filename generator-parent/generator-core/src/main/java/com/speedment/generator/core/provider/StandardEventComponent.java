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
