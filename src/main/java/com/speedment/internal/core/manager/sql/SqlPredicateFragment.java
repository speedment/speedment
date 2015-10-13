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
package com.speedment.internal.core.manager.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class SqlPredicateFragment {

    private String sql;
    private final List<Object> objects;

    public SqlPredicateFragment() {
        this.objects = new ArrayList<>();
        this.sql = "";
    }

    public String getSql() {
        return sql;
    }

    public SqlPredicateFragment setSql(String sql) {
        this.sql = requireNonNull(sql);
        return this;
    }

    public SqlPredicateFragment add(Object o) {
        objects.add(o);
        return this;
    }

    public SqlPredicateFragment addAll(Collection<Object> objects) {
        this.objects.addAll(objects);
        return this;
    }

    public Stream<Object> objects() {
        return objects.stream();
    }

    public static SqlPredicateFragment of(String sql) {
        return new SqlPredicateFragment().setSql(sql);
    }

    public static SqlPredicateFragment of(String sql, Collection<Object> objects) {
        return new SqlPredicateFragment().setSql(sql).addAll(objects);
    }

    public static SqlPredicateFragment of(String sql, Object object) {
        return new SqlPredicateFragment().setSql(sql).add(object);
    }

}
