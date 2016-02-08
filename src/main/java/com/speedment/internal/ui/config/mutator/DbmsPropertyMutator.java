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
package com.speedment.internal.ui.config.mutator;

import com.speedment.internal.core.config.db.mutator.DbmsMutator;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.config.SchemaProperty;
import com.speedment.internal.ui.config.mutator.trait.HasEnabledPropertyMutator;
import com.speedment.internal.ui.config.mutator.trait.HasNamePropertyMutator;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsPropertyMutator extends DbmsMutator<DbmsProperty> implements 
        HasEnabledPropertyMutator<DbmsProperty>, 
        HasNamePropertyMutator<DbmsProperty> {
    
    DbmsPropertyMutator(DbmsProperty dbms) {
        super(dbms);
    }
    
    @Override
    public void setTypeName(String typeName) {
        document().typeNameProperty().setValue(typeName);
    }
    
    @Override
    public void setIpAddress(String ipAddress) {
        document().ipAddressProperty().setValue(ipAddress);
    }
    
    @Override
    public void setPort(Integer port) {
        document().portProperty().setValue(port);
    }
    
    @Override
    public void setUsername(String username) {
        document().usernameProperty().setValue(username);
    }
    
    @Override
    public SchemaProperty addNewSchema() {
        final SchemaProperty child = new SchemaProperty(document());
        document().schemasProperty().add(child);
        return child;
    }
}