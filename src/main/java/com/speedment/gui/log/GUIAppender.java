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
package com.speedment.gui.log;

import javafx.beans.value.WritableStringValue;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.Message;

/**
 *
 * @author Duncan
 */
public final class GUIAppender extends AbstractAppender {
    
    private static final long serialVersionUID = 201506031619L;
    
    private final static String APPENDER_NAME = "Speedment GUI Appender";
    private final WritableStringValue output;
    
    private GUIAppender(WritableStringValue output) {
        super (APPENDER_NAME, new AcceptAllFilter(), PatternLayout.createDefaultLayout());
        this.output = output;
    }

    @Override
    public void append(LogEvent le) {
        println(le.getMessage().getFormattedMessage());
    }

    private void println(String line) {
        output.setValue(output.get() + line + "\n");
    }
    
    public static void setup(Class<?> owner, WritableStringValue output) {
        
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(
            owner.getClassLoader(), true
        );
        
        final Configuration config = ctx.getConfiguration();
        final Appender appender = new GUIAppender(output);
        config.addAppender(appender);
        appender.start();
        
        ctx.updateLoggers();
    }
    
    private static class AcceptAllFilter implements Filter {
        
        private State state;

        @Override
        public Result getOnMismatch() {
            return Result.ACCEPT;
        }

        @Override
        public Result getOnMatch() {
            return Result.ACCEPT;
        }

        @Override
        public Result filter(Logger logger, Level level, Marker marker, String string, Object... os) {
            return Result.ACCEPT;
        }

        @Override
        public Result filter(Logger logger, Level level, Marker marker, Object o, Throwable thrwbl) {
            return Result.ACCEPT;
        }

        @Override
        public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable thrwbl) {
            return Result.ACCEPT;
        }

        @Override
        public void start() {
            state = State.STARTED;
        }

        @Override
        public void stop() {
            state = State.STOPPED;
        }

        @Override
        public boolean isStarted() {
            return state == State.STARTED;
        }

        @Override
        public boolean isStopped() {
            return state != State.STARTED;
        }

        @Override
        public Result filter(LogEvent le) {
            return Result.ACCEPT;
        }

        @Override
        public State getState() {
            return state;
        }
    }
}
