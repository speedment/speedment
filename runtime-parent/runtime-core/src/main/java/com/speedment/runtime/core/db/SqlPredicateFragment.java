/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.db;

import com.speedment.runtime.core.internal.manager.sql.SqlPredicateFragmentImpl;

import java.util.Collection;
import java.util.stream.Stream;

/**
 *
 * @author  Per Minborg
 * @since   2.1.0
 */
public interface SqlPredicateFragment {

     String getSql();

     SqlPredicateFragment setSql(String sql);

     SqlPredicateFragment add(Object o);

     SqlPredicateFragment addAll(Collection<?> extra);

     Stream<Object> objects();

     static SqlPredicateFragment of(String sql) {
        return new SqlPredicateFragmentImpl().setSql(sql);
    }

     static SqlPredicateFragment of(String sql, Collection<Object> objects) {
        return new SqlPredicateFragmentImpl().setSql(sql).addAll(objects);
    }

     static SqlPredicateFragment of(String sql, Object object) {
        return new SqlPredicateFragmentImpl().setSql(sql).add(object);
    }

}
