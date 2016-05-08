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
package com.speedment.tool.event;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.event.Event;
import com.speedment.tool.UISession;

/**
 * Event that is created when the User Interface is starting. This happens when
 * the {@link UISession} instance is accessible but no window have been created
 * yet.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
@Api(version = "2.3")
public final class UiStarting implements Event {
    
    
    
}
