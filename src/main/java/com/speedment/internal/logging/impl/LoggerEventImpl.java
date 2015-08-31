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
package com.speedment.internal.logging.impl;

import com.speedment.internal.logging.Level;
import com.speedment.internal.logging.LoggerEvent;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class LoggerEventImpl implements LoggerEvent {

    private final Level level;
    private final String name;
    private final String message;

    public LoggerEventImpl(Level level, String name, String message) {
        this.level = requireNonNull(level);
        this.name = requireNonNull(name);
        this.message = requireNonNull(message);
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
