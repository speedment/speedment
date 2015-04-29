package com.speedment.orm.function;

/**
 *
 * @author pemi
 */
@Deprecated
public enum StandardBinaryPredicate implements BinaryPredicate {

    EQ {

                @Override
                public <C extends Comparable<C>> boolean test(C first, C second) {
                    //debug(this, first, second);
                    return first.compareTo(second) == 0;
                }

            },
    GT {

                @Override
                public <C extends Comparable<C>> boolean test(C first, C second) {
                    //debug(this, first, second);
                    return first.compareTo(second) > 0;
                }

            }, LT {

                @Override
                public <C extends Comparable<C>> boolean test(C first, C second) {
                    //debug(this, first, second);
                    return first.compareTo(second) < 0;
                }

            };

    private static <C> void debug(BinaryPredicate bo, C first, C second) {
        System.out.println(first + " " + bo + " " + second);
    }

}
