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
package com.speedment.tool.core.brand;

import com.speedment.common.injector.annotation.InjectKey;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A branding container.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@InjectKey(Brand.class)
public interface Brand {

    /**
     * Returns the human-readable web address associated with this brand. Any
     * http-prefix should not be included.
     * <p>
     * Example: {@code www.speedment.org}
     * 
     * @return  the website
     */
    String website();
    
    /**
     * Optionally returns the path to a small logo image fit to be used on icons
     * or in the application titlebar. If no logo is specified, an empty
     * {@link Optional} is returned.
     * <p>
     * Example: {@code /images/speedment_open_source_small.png}
     * 
     * @return  the small logo
     */
    Optional<String> logoSmall();
    
    /**
     * Optionally returns the path to a large logo image fit to be used as an
     * illustration in various dialog messages. If no logo is specified, an 
     * empty {@link Optional} is returned.
     * <p>
     * Example: {@code /images/logo.png}
     * 
     * @return  the larger logo
     */
    Optional<String> logoLarge();
    
    /**
     * Returns a stream of stylesheets that are used when branding.
     * 
     * @return  the stream of stylesheets.
     */
    Stream<String> stylesheets();
}