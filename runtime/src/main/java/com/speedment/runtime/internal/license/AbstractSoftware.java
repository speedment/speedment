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
import com.speedment.runtime.license.Software;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractSoftware implements Software {
    
    private final String name;
    private final String version;
    private final String description; // Can be null.
    private final License license;
    private final boolean internal;
    
    public static Software with(String name, String version, License license, Software... dependencies) {
        return with(name, version, null, license, false, dependencies);
    }
    
    public static Software with(String name, String version, String description, License license, Software... dependencies) {
        return with(name, version, description, license, false, dependencies);
    }
    
    public static Software with(String name, String version, String description, License license, boolean internal, Software... dependencies) {
        if (dependencies.length == 0) {
            return new SoftwareLeaf(name, version, description, license, internal);
        } else {
            return new SoftwareBranch(name, version, description, license, internal, dependencies);
        }
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getVersion() {
        return version;
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public final License getLicense() {
        return license;
    }

    @Override
    public final boolean isInternal() {
        return internal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.version);
        hash = 97 * hash + Objects.hashCode(this.license);
        hash = 97 * hash + (this.internal ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        
        final AbstractSoftware that = (AbstractSoftware) obj;
        return this.internal == that.internal
            && Objects.equals(this.name, that.name)
            && Objects.equals(this.version, that.version)
            && Objects.equals(this.license, that.license);
    }
    
    private AbstractSoftware(String name, String version, String description, License license, boolean internal) {
        this.name        = requireNonNull(name);
        this.version     = requireNonNull(version);
        this.description = description;
        this.license     = requireNonNull(license);
        this.internal    = internal;
    }
    
    private final static class SoftwareBranch extends AbstractSoftware {
        
        private final Software[] dependencies;

        private SoftwareBranch(String name, String version, String description, License license, boolean internal, Software... dependencies) {
            super(name, version, description, license, internal);
            this.dependencies = requireNonNull(dependencies);
        }

        @Override
        public Stream<Software> getDependencies() {
            return Stream.of(dependencies);
        }
        
    }
    
    private final static class SoftwareLeaf extends AbstractSoftware {

        private SoftwareLeaf(String name, String version, String description, License license, boolean internal) {
            super(name, version, description, license, internal);
        }

        @Override
        public Stream<Software> getDependencies() {
            return Stream.empty();
        }
    }
}