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
import com.speedment.component.LoggerFactoryComponent;
import com.speedment.internal.logging.LoggerFactory;
import com.speedment.internal.logging.impl.SystemOutLoggerFactory;
import com.speedment.license.Software;
import java.util.Objects;
import java.util.stream.Stream;

public final class LoggerFactoryComponentImpl extends InternalOpenSourceComponent implements LoggerFactoryComponent {

    private LoggerFactory loggerFactory;

    public LoggerFactoryComponentImpl(Speedment speedment) {
        super(speedment);
        setLoggerFactory(new SystemOutLoggerFactory());
    }

    @Override
    public LoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public void setLoggerFactory(LoggerFactory loggerFactory) {
        this.loggerFactory = Objects.requireNonNull(loggerFactory);
    }
    
    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }
}
