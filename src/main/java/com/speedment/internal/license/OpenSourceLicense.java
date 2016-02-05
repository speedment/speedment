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
package com.speedment.internal.license;

import com.speedment.license.License;
import java.net.MalformedURLException;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public enum OpenSourceLicense implements License {

    APACHE_2(
        "Apache License, Version 2.0", 
        "http://www.apache.org/licenses/LICENSE-2.0"
    ),
    
    BSD_3_CLAUSE(
        "BSD 3-Clause", 
        "https://opensource.org/licenses/BSD-3-Clause"
    ),
    
    CC_BY_2_5(
        "Creative Commons 2.5", 
        "http://creativecommons.org/licenses/by/2.5/"
    ),
    
    ECLIPSE_PUBLIC_LICENSE(
        "Eclipse Public License 1.0", 
        "http://junit.org/license.html"
    ),
    
    GPL_2(
        "GNU General Public License, version 2", 
        "http://www.gnu.org/licenses/gpl-2.0.html",
        "https://www.mysql.com/about/legal/licensing/foss-exception/"
    ),
    
    GPL_3(
        "GNU General Public License, version 3", 
        "http://www.gnu.org/licenses/gpl.html"
    ),
    
    GPL_2_WITH_FOSS(
        "GNU General Public License, version 2 with FOSS Exception", 
        "http://www.gnu.org/licenses/old-licenses/gpl-2.0.html"
    ),
    
    LGPL_3(
        "GNU Lesser General Public License, version 3",
        "http://www.gnu.org/licenses/lgpl-3.0.html"
    ),
    
    MIT(
        "MIT License 1.0",
        "https://opensource.org/licenses/MIT"
    );
    
    private final String name;
    private final URL[] urls;
    
    OpenSourceLicense(String name, String... urls) {
        this.name = requireNonNull(name);
        
        this.urls = new URL[urls.length];
        try {
            for (int i = 0; i < urls.length; i++) {
                this.urls[i] = new URL(urls[i]);
            }
        } catch (final MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Stream<URL> getSources() {
        return Stream.of(urls);
    }

    @Override
    public Commercial isCommercial() {
        return Commercial.OPEN_SOURCE;
    }
}