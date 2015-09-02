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
package com.speedment.internal.core.pool.impl;

import com.speedment.internal.core.pool.PoolableConnection;
import java.sql.Connection;
import java.sql.SQLException;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author pemi
 */
public final class PoolableConnectionImpl extends PoolableConnectionDelegator implements PoolableConnection {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private final long id;
    private final String user;
    private final String password;
    private final String uri;
    private final long created;
    private final long expires;
    private Runnable onClose;

    public PoolableConnectionImpl(String uri, String user, String password, Connection connection, long expires) {
        super(connection);
        this.id = ID_GENERATOR.getAndIncrement();
        this.uri = requireNonNull(uri);
        this.user = user; // Nullable
        this.password = password; //nullable
        this.created = System.currentTimeMillis();
        this.expires = expires;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void close() throws SQLException {
        onClose();
    }

    @Override
    public void rawClose() throws SQLException {
        connection.close();
    }

    @Override
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @Override
    public void onClose() {
        onClose.run();
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public long getCreated() {
        return created;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public long getExpires() {
        return expires;
    }

}
