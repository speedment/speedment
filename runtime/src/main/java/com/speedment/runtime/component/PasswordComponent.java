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
package com.speedment.runtime.component;

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Dbms;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
@InjectorKey(PasswordComponent.class)
public interface PasswordComponent extends Component {
    
    void put(String dbmsName, char[] password);
    
    default void put(Dbms dbms, char[] password) {
        put(dbms.getName(), password);
    }
    
    Optional<char[]> get(String dbmsName);
    
    default Optional<char[]> get(Dbms dbms) {
        return get(dbms.getName());
    }
}