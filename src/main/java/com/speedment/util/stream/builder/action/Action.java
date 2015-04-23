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
package com.speedment.util.stream.builder.action;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <R>
 */
public class Action<T extends BaseStream<?, T>, R extends BaseStream<?, R>> implements Supplier<Function<T, R>> {

    private final Function<T, R> mapper;
    private final Class<? extends BaseStream> resultStreamClass;
//    private final Set<? extends ActionProperty> actionProperties;
    private final Map<Verb, Set<Property>> streamImpacts;

//    protected Action(Function<T, R> mapper, Class<? extends BaseStream> resultStreamClass) {
//        this(mapper, resultStreamClass, Collections.emptySet());
//    }

//    protected Action(Function<T, R> mapper, Class<? extends BaseStream> resultStreamClass, Set<? extends ActionProperty> actionProperties) {
//        this.mapper = mapper;
//        this.resultStreamClass = resultStreamClass;
////        this.actionProperties = actionProperties;
//        this.streamImpacts = new HashMap<>();
//    }
    
    protected Action(Function<T, R> mapper, Class<? extends BaseStream> resultStreamClass, BasicAction basicAction) {
        this.mapper = mapper;
        this.resultStreamClass = resultStreamClass;
        this.streamImpacts = new HashMap<>();
        set(basicAction);
    }

    @Override
    public Function<T, R> get() {
        return mapper;
    }

    public Class<? extends BaseStream> resultStreamClass() {
        return resultStreamClass;
    }

    protected void set(BasicAction basicAction) {
        basicAction.statements().forEach(this::set);
    }

    protected void set(Verb verb, Property property) {
        aquireSet(verb).add(property);
    }

    protected void set(Statement statement) {
        set(statement.getVerb(), statement.getProperty());
    }

    protected void set(Statement firstStatement, Statement... otherStatements) {
        set(firstStatement);
        Stream.of(otherStatements).forEach(this::set);
    }

    public boolean is(Verb verb, Property property) {
        return aquireSet(verb).contains(property);
    }

    public boolean is(Statement statement) {
        return aquireSet(statement.getVerb()).contains(statement.getProperty());
    }

    public boolean is(Statement firstStatement, Statement... otherStatements) {
        if (!is(firstStatement)) {
            return false;
        }
        return Stream.of(otherStatements).allMatch(this::is);
    }

    protected Set<Property> aquireSet(Verb verb) {
        return streamImpacts.computeIfAbsent(verb, v -> EnumSet.noneOf(Property.class));
    }

//    public boolean is(ActionProperty actionProperty) {
//        return actionProperties.contains(actionProperty);
//    }

    /**
     * Returns <code>true</code> if this Actions contains all the
     * ActionProperties provided, otherwise <code>false</code>.
     *
     * @param actionProperty
     * @return <code>true</code> if this Actions contains all the
     * ActionProperties provided, otherwise <code>false</code>
     */
//    public boolean is(Set<? super ActionProperty> actionProperty) {
//        return actionProperties.containsAll(actionProperty);
//    }

    @Override
    public String toString() {
        final String className = getClass().getSimpleName();
        final int index = className.lastIndexOf(Action.class.getSimpleName());
        if (index > 0) {
            return className.substring(0, index);
        }
        return className;
    }

}
