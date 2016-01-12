/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.BaseDocument;
import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectImpl extends BaseDocument implements Project {

    public ProjectImpl(Map<String, Object> data) {
        super(data);
    }

    @Override
    public Optional<? extends Document> getParent() {
        return Optional.empty();
    }
    
    @Override
    public Stream<Document> ancestors() {
        return Stream.empty();
    }

    @Override
    public BiFunction<Project, Map<String, Object>, Dbms> dbmsConstructor() {
        return DbmsImpl::new;
    }
}