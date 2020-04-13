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

package com.speedment.test.connector.support;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.DbmsUtil;
import com.speedment.runtime.config.trait.HasNameUtil;

import java.util.HashMap;
import java.util.Map;

public final class Dummies {

    private Dummies() {
    }

    public static Dbms dbms() {
        final Map<String, Object> data = new HashMap<>();
        data.put(DbmsUtil.IP_ADDRESS, "localhost");
        data.put(DbmsUtil.PORT, 3066);
        data.put(DbmsUtil.USERNAME, "username");
        data.put(DbmsUtil.SERVER_NAME, "server");
        data.put(HasNameUtil.NAME, "name");

        return Dbms.create(null, data);
    }
}
