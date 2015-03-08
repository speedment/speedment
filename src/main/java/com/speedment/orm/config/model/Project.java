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
import com.speedment.orm.config.model.impl.ProjectImpl;
import groovy.lang.Closure;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Project extends ConfigEntity, Parent<Dbms>, Child<ProjectManager> {

    enum Holder {

        HOLDER;
        private Supplier<Project> provider = () -> new ProjectImpl();
    }

    static void setSupplier(Supplier<Project> provider) {
        Holder.HOLDER.provider = provider;
    }

    static Project newProject() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    default Class<Project> getInterfaceMainClass() {
        return Project.class;
    }

    @Override
    default Class<ProjectManager> getParentInterfaceMainClass() {
        return ProjectManager.class;
    }

    default Dbms addNewDbms() {
        final Dbms e = Dbms.newDbms();
        add(e);
        return e;
    }

    @External
    String getPacketName();

    @External
    void setPacketName(String packetName);

    @External
    String getPacketLocation();

    @External
    void setPacketLocation(String packetLocation);

    // Groovy
    default Dbms dbms(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewDbms);
    }

}
