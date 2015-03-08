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
package com.speedment.orm.config.model;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.aspects.Parent;
import com.speedment.orm.config.model.aspects.Child;
import com.speedment.orm.config.model.parameters.DbmsTypeable;
import com.speedment.orm.platform.SpeedmentPlatform;
import groovy.lang.Closure;
import java.util.Optional;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Dbms extends ConfigEntity, DbmsTypeable, Child<Project>, Parent<Schema> {

    @Override
    default Class<Dbms> getInterfaceMainClass() {
        return Dbms.class;
    }

    @Override
    default Class<Project> getParentInterfaceMainClass() {
        return Project.class;
    }

    default Schema addNewSchema() {
        final Schema e = SpeedmentPlatform.getInstance().getConfigEntityFactory().newSchema();
        add(e);
        return e;
    }

    @External
    Optional<String> getIpAddress();

    @External
    void setIpAddress(String ipAddress);

    @External
    Optional<Integer> getPort();

    @External
    void setPort(Integer port);

    @External
    Optional<String> getUsername();

    @External
    void setUsername(String name);

    @External
    Optional<String> getPassword();

    @External
    void setPassword(String password);

    // Groovy
    default Schema schema(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewSchema);
    }
}
