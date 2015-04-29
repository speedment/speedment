package com.speedment.orm.function;

import java.util.function.Predicate;

/**
 *
 * @author pemi
 */
@Deprecated
public enum StandardUnaryPredicate implements Predicate<Object> {

    IS_NULL {

                @Override
                public boolean test(Object t) {
                    return t == null;
                }

            },
    IS_NOT_NULL {

                @Override
                public boolean test(Object t) {
                    return t != null;
                }

            },
    TRUE {

                @Override
                public boolean test(Object t) {
                    return true;
                }

            },
    FALSE {

                @Override
                public boolean test(Object t) {
                    return false;
                }

            }

}
