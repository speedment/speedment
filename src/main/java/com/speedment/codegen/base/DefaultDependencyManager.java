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
package com.speedment.codegen.base;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import static java.util.Collections.emptySet;
import java.util.Optional;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 * Default implementation of the {@link DependencyManager} interface.
 *
 * @author Emil Forslund
 */
public class DefaultDependencyManager implements DependencyManager {

    private final Set<String> dependencies = new HashSet<>();
    private final Set<Pattern> ignorePatterns;
    private String currentPackage;

    /**
     * Initializes the DependencyManager.
     */
    public DefaultDependencyManager() {
        ignorePatterns = emptySet();
    }

    /**
     * Initializes the DependencyManager.
     *
     * @param ignoredPatterns to add to the ignore list
     */
    public DefaultDependencyManager(Pattern... ignoredPatterns) {
        ignorePatterns = Stream.of(ignoredPatterns)
            .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean load(String fullname) {
        return dependencies.add(fullname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded(String fullname) {
        return dependencies.contains(fullname);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearDependencies() {
        dependencies.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIgnored(String fullname) {
        return  ignorePatterns.stream()
            .map(Pattern::asPredicate)
            .anyMatch(p -> p.test(fullname));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setCurrentPackage(String pack) {
        if (currentPackage == null) {
            currentPackage = pack;
            return true;
        } else return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unsetCurrentPackage(String pack) {
        if (currentPackage != null && currentPackage.equals(pack)) {
            currentPackage = null;
            return true;
        } else return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getCurrentPackage() {
        return Optional.ofNullable(currentPackage);
    }
}