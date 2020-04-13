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
package com.speedment.tool.core.event;

import com.speedment.generator.core.event.Event;

/**
 * An enumeration of standard events caused by the user interface.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public enum UIEvent implements Event {
    
    /**
     * The user interface has started but no window has been opened yet.
     */
    STARTED,
    
    /**
     * The "Mail Prompt Window" has just been opened.
     */
    OPEN_MAIL_PROMPT_WINDOW,
    
    /**
     * The "Connect Window" has just been opened.
     */
    OPEN_CONNECT_WINDOW,
    
    /**
     * The "Main Window" has just been opened.
     */
    OPEN_MAIN_WINDOW,
    
    /**
     * The "Components Window" has just been opened.
     */
    OPEN_COMPONENTS_WINDOW,
    
    /**
     * The "About Window" has just been opened.
     */
    OPEN_ABOUT_WINDOW

}