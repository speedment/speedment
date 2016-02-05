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
package com.speedment.license;

import com.speedment.annotation.Api;
import java.net.URL;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Trait used to separate open source from commercial components.
 *
 * @author     Emil Forslund
 */
@Api(version = "2.3")
public interface License {
    
    final Comparator<License> COMPARATOR = 
        Comparator.comparing(License::isCommercial)
            .thenComparing(License::getName);
    
    enum Commercial {
        PROPRIETARY,
        OPEN_SOURCE
    }

    /**
     * Returns the license name.
     *
     * @return the license name
     */
    String getName();
    
    /**
     * Returns an array of external sources for this license.
     * 
     * @return  the license file URLs
     */
    Stream<URL> getSources();

    /**
     * Returns if this is a commercial license.
     *
     * @return if this is a commercial license`
     */
    Commercial isCommercial();
}