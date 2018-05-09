/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.example.typemapper;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.translator.component.TypeMapperComponent;

/**
 *
 * @author Per Minborg
 */
public class CustomTypeMapperInstaller {

    @ExecuteBefore(RESOLVED)
    public void onResolve(@WithState(INITIALIZED) TypeMapperComponent typeMapper) {
        typeMapper.install(Integer.class, IntegerZeroOneToYesNoTypeMapper::new);
        typeMapper.install(int.class, IntegerZeroOneToYesNoTypeMapper::new);
    }    
}
