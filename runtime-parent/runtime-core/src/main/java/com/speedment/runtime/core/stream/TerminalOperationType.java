package com.speedment.runtime.core.stream;

/**
 *
 * @author Per Minborg
 */
public enum TerminalOperationType {

    /* REFERENCE STREAM */
    FOR_EACH,                /// .updater(), persister(), remover()
    FOR_EACH_ORDERED,        /// .updater(), persister(), remover()
    TO_ARRAY,                /// 
    TO_ARRAY_WITH_GENERATOR, /// 
    REDUCE,
    REDUCE_WITH_IDENTITY(),
    REDUCE_WITH_IDENTITY_AND_COMBINER(),
    COLLECT,
    COLLECT_WITH_SUPPLIER_AND_ACCUMULATOR_AND_COMBINER,
    MIN,
    MAX,
    COUNT,                   /// Use SQL count if possible, order irrelevant
    ANY_MATCH,               /// Check if all filters and match predicate
    ALL_MATCH,               /// Count all with filters and all with filters and match predicate and see if they are the same
    NONE_MATCH,              /// Count all with filters and match predicate and see if it is zero
    FIND_FIRST,              /// Limit 1
    FIND_ANY,                /// Remove all ordering from pipeline
    TAKE_WHILE,
    DROP_WHILE,
    /* Primitive Stream Specific */
    SUM,                     /// Use SQL sum() if possible, order irrelevant
    AVERAGE,                 /// Use SQL average() if possible, order irrelevant
    SUMMARY_STATISTICS;      /// Use count(), sum(), min() and max() and create SummaryStatistics with reflection. Order irrelevant

}
