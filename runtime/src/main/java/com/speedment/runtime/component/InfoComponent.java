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
package com.speedment.runtime.component;

import com.speedment.runtime.annotation.Api;
import com.speedment.common.injector.annotation.InjectKey;

/**
 * Contains general information about the installment like the software title
 * and version. This is used to print correct messages for an example when the
 * application launches.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
@Api(version = "3.0")
@InjectKey(InfoComponent.class)
public interface InfoComponent extends Component {
    
    /**
     * The title of Speedment.
     * 
     * @return  the title
     */
    String title();
    
    /**
     * The subtitle of Speedment.
     * 
     * @return  the subtitle
     */
    String subtitle();
    
    /**
     * The version of Speedment.
     * 
     * @return  the version
     */
    String version();

    /**
     * {@inheritDoc} 
     */
    @Override
    public default Class<InfoComponent> getComponentClass() {
        return InfoComponent.class;
    }
}