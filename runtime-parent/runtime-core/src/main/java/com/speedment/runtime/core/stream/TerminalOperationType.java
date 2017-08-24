/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.stream;

/**
 *
 * @author Per Minborg
 */
@Deprecated
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
    
    /* Primitive Stream Specific */
    SUM,                     /// Use SQL sum() if possible, order irrelevant
    AVERAGE,                 /// Use SQL average() if possible, order irrelevant
    SUMMARY_STATISTICS;      /// Use count(), sum(), min() and max() and create SummaryStatistics with reflection. Order irrelevant

}
