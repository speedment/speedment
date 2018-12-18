package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.internal.manager.sql.AbstractFieldPredicateView;
import com.speedment.runtime.field.predicate.FieldPredicate;

import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRaw;

/**
 * @author Emil Forslund
 * @since  3.1.9
 */
public class SqliteFieldPredicateView extends AbstractFieldPredicateView {

    @Override
    protected SqlPredicateFragment equalHelper(String cn, Object argument) {
        return of("(" + cn + " = ?)").add(argument);
    }

    @Override
    protected SqlPredicateFragment notEqualHelper(String cn, Object argument) {
        return of("(" + cn + " != ?)").add(argument);
    }

    @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE " + (negated ? "= ?)" : "!= ?)"))
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE (? || \"%\") ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE LIKE (? || \"%\") ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE (\"%\" || ?) ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE LIKE (\"%\" || ?) ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE (\"%\" || ? || \"%\") ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE LIKE (\"%\" || ? || \"%\") ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }
}
