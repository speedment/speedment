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
package com.speedment.runtime.internal.license;

import com.speedment.runtime.license.License;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Arrays.deepHashCode(this.urls);
        hash = 97 * hash + Objects.hashCode(this.commercial);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        else if (obj == null) { return false; }
        else if (getClass() != obj.getClass()) { return false; }
        
        final AbstractLicense other = (AbstractLicense) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        } else if (!Arrays.deepEquals(this.urls, other.urls)) {
            return false;
        } else return this.commercial == other.commercial;
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