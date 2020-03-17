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
package com.speedment.runtime.core.internal.manager.sql;

import com.speedment.runtime.core.db.SqlPredicateFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public class SqlPredicateFragmentImpl implements SqlPredicateFragment {

    private String sql;
    private final List<Object> objects;

    public SqlPredicateFragmentImpl() {
        this.objects = new ArrayList<>();
        this.sql = "";
    }

    @Override
    public String getSql() {
        return sql;
    }

    @Override
    public SqlPredicateFragmentImpl setSql(String sql) {
        this.sql = requireNonNull(sql);
        return this;
    }

    @Override
    public SqlPredicateFragmentImpl add(Object o) {
        objects.add(o);
        return this;
    }

    @Override
    public SqlPredicateFragmentImpl addAll(Collection<?> extra) {
        objects.addAll(extra);
        return this;
    }

    @Override
    public Stream<Object> objects() {
        return objects.stream();
    }

}
