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

package com.speedment.runtime.config;

import static com.speedment.runtime.config.DbmsUtil.CONNECTION_URL;
import static com.speedment.runtime.config.DbmsUtil.IP_ADDRESS;
import static com.speedment.runtime.config.DbmsUtil.LOCAL_PATH;
import static com.speedment.runtime.config.DbmsUtil.PORT;
import static com.speedment.runtime.config.DbmsUtil.SERVER_NAME;
import static com.speedment.runtime.config.DbmsUtil.TYPE_NAME;
import static com.speedment.runtime.config.DbmsUtil.USERNAME;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.config.exception.SpeedmentConfigException;
import org.junit.jupiter.api.Test;

final class DbmsTest {

    @Test
    void getTypeName() {
        final Dbms dbms = Dbms.create(null, singletonMap(TYPE_NAME, "dbms_type"));
        assertNotNull(dbms.getTypeName());

        final Dbms faultyDbms = Dbms.create(null, emptyMap());
        assertThrows(SpeedmentConfigException.class, faultyDbms::getTypeName);
    }

    @Test
    void getIpAddress() {
        final Dbms dbms = Dbms.create(null, singletonMap(IP_ADDRESS, "ip_address"));
        assertTrue(dbms.getIpAddress().isPresent());

        final Dbms faultyDbms = Dbms.create(null, emptyMap());
        assertFalse(faultyDbms.getIpAddress().isPresent());
    }

    @Test
    void getPort() {
        final Dbms dbms = Dbms.create(null, singletonMap(PORT, 0));
        assertTrue(dbms.getPort().isPresent());

        final Dbms faultyDbms = Dbms.create(null, emptyMap());
        assertFalse(faultyDbms.getPort().isPresent());
    }

    @Test
    void getLocalPath() {
        final Dbms dbms = Dbms.create(null, singletonMap(LOCAL_PATH, "/local/path"));
        assertTrue(dbms.getLocalPath().isPresent());

        final Dbms faultyDbms = Dbms.create(null, emptyMap());
        assertFalse(faultyDbms.getLocalPath().isPresent());
    }

    @Test
    void getConnectionUrl() {
        final Dbms dbms = Dbms.create(null, singletonMap(CONNECTION_URL, "https://connection.url/"));
        assertTrue(dbms.getConnectionUrl().isPresent());

        final Dbms faultyDbms = Dbms.create(null, emptyMap());
        assertFalse(faultyDbms.getConnectionUrl().isPresent());
    }

    @Test
    void getUsername() {
        final Dbms dbms = Dbms.create(null, singletonMap(USERNAME, "username"));
        assertTrue(dbms.getUsername().isPresent());

        final Dbms faultyDbms = Dbms.create(null, emptyMap());
        assertFalse(faultyDbms.getUsername().isPresent());
    }

    @Test
    void getServerName() {
        final Dbms dbms = Dbms.create(null, singletonMap(SERVER_NAME, "server"));
        assertTrue(dbms.getServerName().isPresent());

        final Dbms faultyDbms = Dbms.create(null, emptyMap());
        assertFalse(faultyDbms.getServerName().isPresent());
    }

    @Test
    void mainInterface() {
        final Dbms dbms = Dbms.create(null, emptyMap());

        assertEquals(Dbms.class, dbms.mainInterface());
    }

    @Test
    void mutator() {
        final Dbms dbms = Dbms.create(null, emptyMap());

        assertNotNull(dbms.mutator());
    }

    @Test
    void deepCopy() {
        final Dbms dbms = Dbms.create(null, emptyMap());

        assertNotNull(dbms.deepCopy());
    }
}
