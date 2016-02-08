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
package com.speedment.config.db.trait;

import com.speedment.config.Document;

/**
 *
 * @author Per Minborg
 */
public interface HasMainInterface extends Document {

        /**
     * Returns the {@code Class} of the interface of this node.
     * <p>
     * This should <b>not</b> be overridden by implementing classes!
     *
     * @return the main interface class
     */
    Class<? extends Document> mainInterface();
    
}
