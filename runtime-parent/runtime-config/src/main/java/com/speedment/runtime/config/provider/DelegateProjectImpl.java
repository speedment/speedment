/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

package com.speedment.runtime.config.provider;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.internal.BaseDocument;
import com.speedment.runtime.config.internal.ProjectImpl;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class DelegateProjectImpl extends BaseDocument implements Project {

    private ProjectImpl project;

    public DelegateProjectImpl(Map<String, Object> data) {
        super(null, data);
        this.project = new ProjectImpl(data);
    }

    @Override
    public String getName() {
        return project.getName();
    }

    @Override
    public Optional<? extends Document> getParent() {
        return project.getParent();
    }

    @Override
    public Stream<Document> ancestors() {
        return project.ancestors();
    }

    @Override
    public Stream<Dbms> dbmses() {
        return project.dbmses();
    }
}
