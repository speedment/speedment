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
package com.speedment.internal.core.platform.component;

import com.speedment.internal.logging.LoggerFactory;

/**
 * The loggerFactoryComponent provides the logger factory that the Speedment
 * framework is using. You can plug in your own LoggerFactory if needed.
 *
 * @author pemi
 */
public interface LoggerFactoryComponent extends Component {

    @Override
    default Class<LoggerFactoryComponent> getComponentClass() {
        return LoggerFactoryComponent.class;
    }

    /**
     * Returns the current LoggerFactory.
     *
     * @return the current LoggerFactory
     */
    LoggerFactory getLoggerFactory();

    /**
     * Sets the LoggerFactory to use.
     *
     * @param loggerFactory to use
     */
    void setLoggerFactory(LoggerFactory loggerFactory);

}
