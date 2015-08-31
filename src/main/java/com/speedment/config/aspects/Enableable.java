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
package com.speedment.config.aspects;

import com.speedment.annotation.Api;
import com.speedment.annotation.External;

/**
 * A trait-like interface for nodes that can be enabled and disabled.
 * 
 * @author Emil Forslund
 */
@Api(version = "2.1")
public interface Enableable {
    
    /**
     * Enable or disable this node. This will have no effect if the node is
     * already in the desired state.
     * 
     * @param enabled  <code>true</code> if this node is to be enabled.
     */
    @External(type = Boolean.class)
    void setEnabled(Boolean enabled);
    
    /**
     * Returns whether or not this node is enabled.
     * 
     * @return <code>true</code> if this node is enabled.
     */
    @External(type = Boolean.class)
    Boolean isEnabled();
}