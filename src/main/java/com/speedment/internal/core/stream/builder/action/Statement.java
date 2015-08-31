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
package com.speedment.internal.core.stream.builder.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author pemi
 */
public final class Statement {

    private final Verb verb;
    private final Property property;
    private final static Map<Statement, Statement> SINGLETONS = new HashMap<>();

    private Statement(Verb verb, Property property) {
        this.verb = requireNonNull(verb);
        this.property = requireNonNull(property);
    }

    @Override
    public String toString() {
        return getVerb() + " " + getProperty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVerb(), getProperty());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Statement other = (Statement) obj;
        if (this.getVerb() != other.getVerb()) {
            return false;
        }
        if (this.getProperty() != other.getProperty()) {
            return false;
        }
        return true;
    }

    public Verb getVerb() {
        return verb;
    }

    public Property getProperty() {
        return property;
    }

    public static Statement of(Verb verb, Property propery) {
        requireNonNull(verb);
        requireNonNull(propery);
        return SINGLETONS.computeIfAbsent(new Statement(verb, propery), Function.identity());
    }

}
