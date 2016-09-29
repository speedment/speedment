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
package com.speedment.common.codegen.internal;

import com.speedment.common.codegen.DependencyManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

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

    @Override
    public boolean load(String fullname) {
        return dependencies.add(requireNonNull(fullname));
    }

    @Override
    public boolean isLoaded(String fullname) {
        return dependencies.contains(requireNonNull(fullname));
    }

    @Override
    public void clearDependencies() {
        dependencies.clear();
    }

    @Override
    public boolean isIgnored(String fullname) {
        return  ignorePatterns.stream()
            .map(Pattern::asPredicate)
            .anyMatch(p -> p.test(requireNonNull(fullname)));
    }

    @Override
    public boolean setCurrentPackage(String pack) {
        if (currentPackage == null) {
            currentPackage = requireNonNull(pack);
            return true;
        } else return false;
    }

    @Override
    public boolean unsetCurrentPackage(String pack) {
        if (currentPackage != null 
        &&  currentPackage.equals(requireNonNull(pack))) {
            currentPackage = null;
            return true;
        } else return false;
    }

    @Override
    public Optional<String> getCurrentPackage() {
        return Optional.ofNullable(currentPackage);
    }
}