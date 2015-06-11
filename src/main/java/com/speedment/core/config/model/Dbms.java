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
package com.speedment.core.config.model;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.aspects.Parent;
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.impl.DbmsImpl;
import com.speedment.core.config.model.parameters.DbmsTypeable;
import com.speedment.core.db.DbmsHandler;
import groovy.lang.Closure;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Dbms extends ConfigEntity, DbmsTypeable, Child<Project>, Parent<Schema> {

    enum Holder {

        HOLDER;
        private Supplier<Dbms> provider = () -> new DbmsImpl();
    }

    static void setSupplier(Supplier<Dbms> provider) {
        Holder.HOLDER.provider = provider;
    }

    static Dbms newDbms() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<Dbms> getInterfaceMainClass() {
        return Dbms.class;
    }

    @Override
    default Class<Project> getParentInterfaceMainClass() {
        return Project.class;
    }

    default Schema addNewSchema() {
        final Schema e = Schema.newSchema();
        add(e);
        return e;
    }

    @External(type = String.class)
    Optional<String> getIpAddress();

    @External(type = String.class)
    void setIpAddress(String ipAddress);

    @External(type = Integer.class)
    Optional<Integer> getPort();

    @External(type = Integer.class)
    void setPort(Integer port);

    @External(type = String.class)
    Optional<String> getUsername();

    @External(type = String.class)
    void setUsername(String name);

    @External(type = String.class, isSecret = true)
    Optional<String> getPassword();

    @External(type = String.class, isSecret = true)
    void setPassword(String password);

    // Groovy
    default Schema schema(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewSchema);
    }

    Optional<DbmsHandler> getDbmsHandler();

    void setDbmsHandler(DbmsHandler dbmsHandler);

}
