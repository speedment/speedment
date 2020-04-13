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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.config.exception.SpeedmentConfigException;
import org.junit.jupiter.api.Test;

final class DbmsTest extends BaseConfigTest<Dbms> {

    @Override
    Dbms getDocumentInstance() {
        return Dbms.create(null, map());
    }

    @Test
    void getTypeName() {
        final Dbms dbms = Dbms.create(null, map(entry(TYPE_NAME, "dbms_type")));
        assertNotNull(dbms.getTypeName());

        assertThrows(SpeedmentConfigException.class, getDocumentInstance()::getTypeName);
    }

    @Test
    void getIpAddress() {
        final Dbms dbms = Dbms.create(null, map(entry(IP_ADDRESS, "ip_address")));
        assertTrue(dbms.getIpAddress().isPresent());

        assertFalse(getDocumentInstance().getIpAddress().isPresent());
    }

    @Test
    void getPort() {
        final Dbms dbms = Dbms.create(null, map(entry(PORT, 0)));
        assertTrue(dbms.getPort().isPresent());

        assertFalse(getDocumentInstance().getPort().isPresent());
    }

    @Test
    void getLocalPath() {
        final Dbms dbms = Dbms.create(null, map(entry(LOCAL_PATH, "/local/path")));
        assertTrue(dbms.getLocalPath().isPresent());

        assertFalse(getDocumentInstance().getLocalPath().isPresent());
    }

    @Test
    void getConnectionUrl() {
        final Dbms dbms = Dbms.create(null, map(entry(CONNECTION_URL, "https://connection.url/")));
        assertTrue(dbms.getConnectionUrl().isPresent());

        assertFalse(getDocumentInstance().getConnectionUrl().isPresent());
    }

    @Test
    void getUsername() {
        final Dbms dbms = Dbms.create(null, map(entry(USERNAME, "username")));
        assertTrue(dbms.getUsername().isPresent());

        assertFalse(getDocumentInstance().getUsername().isPresent());
    }

    @Test
    void getServerName() {
        final Dbms dbms = Dbms.create(null, map(entry(SERVER_NAME, "server")));
        assertTrue(dbms.getServerName().isPresent());

        assertFalse(getDocumentInstance().getServerName().isPresent());
    }
}
