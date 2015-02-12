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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.*;
import java.util.Objects;

/**
 *
 * @author pemi
 */
public class ProjectImpl extends AbstractConfigEntity<Project, ProjectManager, Dbms> implements Project {

    private String packetName, packetLocation;

    @Override
    protected void setDefaults() {
        setPacketLocation("src/main/java");
        setPacketName("com.company.speedment.orm.test");
    }

    @Override
    public String getPacketName() {
        return packetName;
    }

    @Override
    public Project setPacketName(CharSequence packetName) {
        return with(Objects.requireNonNull(packetName), pn -> this.packetName = makeNullSafeString(pn));
    }

    @Override
    public String getPacketLocation() {
        return packetLocation;
    }

    @Override
    public Project setPacketLocation(CharSequence packetLocation) {
        return with(Objects.requireNonNull(packetLocation), pl -> this.packetLocation = makeNullSafeString(pl));
    }

}
