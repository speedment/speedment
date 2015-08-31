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
import com.speedment.internal.logging.impl.formatter.StandardFormatters;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class NoOpLogger extends AbstractLogger {

    public NoOpLogger() {
        super("", StandardFormatters.PLAIN_FORMATTER);
    }

    @Override
    protected void output(String message) {
    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message) {

    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg) {

    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg1, Object arg2) {

    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg1, Object arg2, Object arg3) {

    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg1, Object arg2, Object arg3, Object... args) {

    }

}
