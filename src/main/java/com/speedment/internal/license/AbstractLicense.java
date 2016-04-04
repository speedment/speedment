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
public abstract class AbstractLicense implements License {
    
    private final String name;
    private final URL[] urls;
    private final Type commercial;

    public static License openSource(String name, String... sources) {
        return new AbstractLicense(name, Type.OPEN_SOURCE, sources) {};
    }
    
    public static License proprietary(String name, String... sources) {
        return new AbstractLicense(name, Type.PROPRIETARY, sources) {};
    }
    
    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Stream<URL> getSources() {
        return Stream.of(urls);
    }

    @Override
    public final Type getType() {
        return commercial;
    }
    
    private AbstractLicense(String name, Type commercial, String... sources) {
        this.name       = requireNonNull(name);
        this.commercial = requireNonNull(commercial);
        
        this.urls = new URL[sources.length];
        try {
            for (int i = 0; i < sources.length; i++) {
                this.urls[i] = new URL(sources[i]);
            }
        } catch (final MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}