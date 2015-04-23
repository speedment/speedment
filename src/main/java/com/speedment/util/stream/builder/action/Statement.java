package com.speedment.util.stream.builder.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author pemi
 */
public final class Statement {

    private final Verb verb;
    private final Property property;
    private final static Map<Statement, Statement> singletons = new HashMap<>();

    private Statement(Verb verb, Property property) {
        this.verb = verb;
        this.property = property;
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
        return singletons.computeIfAbsent(new Statement(verb, propery), Function.identity());
    }

}
