/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.license;

import com.speedment.license.License;
import com.speedment.license.Software;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractSoftware implements Software {
    
    private final String name;
    private final String version;
    private final License license;
    
    public static Software with(String name, String version, License license, Software... dependencies) {
        if (dependencies.length == 0) {
            return new SoftwareLeaf(name, version, license);
        } else {
            return new SoftwareBranch(name, version, license, dependencies);
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
    public final License getLicense() {
        return license;
    }
    
    private AbstractSoftware(String name, String version, License license) {
        this.name    = requireNonNull(name);
        this.version = requireNonNull(version);
        this.license = requireNonNull(license);
    }
    
    private final static class SoftwareBranch extends AbstractSoftware {
        
        private final Software[] dependencies;

        private SoftwareBranch(String name, String version, License license, Software... dependencies) {
            super(name, version, license);
            this.dependencies = requireNonNull(dependencies);
        }

        @Override
        public Stream<Software> getDependencies() {
            return Stream.of(dependencies);
        }
        
    }
    
    private final static class SoftwareLeaf extends AbstractSoftware {

        private SoftwareLeaf(String name, String version, License license) {
            super(name, version, license);
        }

        @Override
        public Stream<Software> getDependencies() {
            return Stream.empty();
        }
    }
}