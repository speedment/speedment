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
package com.speedment.orm.platform.component;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface PrimaryKeyFactoryComponent extends Component {

    // Identity function by default
    default <KEY> KEY make(KEY key) {
        return key;
    }

    <K0, K1> List<?> make(K0 k0, K1 k1);

    <K0, K1, K2> List<?> make(K0 k0, K1 k1, K2 k2);

    <K0, K1, K2, K3> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3);

    <K0, K1, K2, K3, K4> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3, K4 k4);

    <K0, K1, K2, K3, K4> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3, K4 k4, Object... otherKeys);

}
