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
package com.speedment.tool.config.mutator.trait;


import com.speedment.runtime.config.mutator.trait.HasEnabledMutator;
import com.speedment.tool.config.trait.HasEnabledProperty;

/**
 *
 * @param <DOC>  document type
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */

public interface HasEnabledPropertyMutator<DOC extends HasEnabledProperty> extends HasEnabledMutator<DOC> {
    
    @Override
    default void setEnabled(Boolean enabled) {
        document().enabledProperty().setValue(enabled);
    }
}