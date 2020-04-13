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

package com.speedment.runtime.config.mutator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasIdMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasNameMutatorMixin;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

final class DbmsMutatorTest implements
        HasEnabledMutatorMixin<Dbms, DbmsMutator<Dbms>>,
        HasIdMutatorMixin<Dbms, DbmsMutator<Dbms>>,
        HasNameMutatorMixin<Dbms, DbmsMutator<Dbms>> {

    @Override
    @SuppressWarnings("unchecked")
    public DbmsMutator<Dbms> getMutatorInstance() {
        return (DbmsMutator<Dbms>) Dbms.create(null, new HashMap<>()).mutator();
    }

    @Test
    void setTypeName() {
        assertDoesNotThrow(() -> getMutatorInstance().setTypeName("type"));
    }

    @Test
    void setIpAddress() {
        assertDoesNotThrow(() -> getMutatorInstance().setIpAddress("128.0.0.1"));
    }

    @Test
    void setPort() {
        assertDoesNotThrow(() -> getMutatorInstance().setPort(8080));
    }

    @Test
    void setLocalPath() {
        assertDoesNotThrow(() -> getMutatorInstance().setLocalPath("/path"));
    }

    @Test
    void setUsername() {
        assertDoesNotThrow(() -> getMutatorInstance().setUsername("username"));
    }

    @Test
    void setConnectionUrl() {
        assertDoesNotThrow(() -> getMutatorInstance().setConnectionUrl("https://connection.url"));
    }

    @Test
    void addNewSchema() {
        assertNotNull(getMutatorInstance().addNewSchema());
    }
}
