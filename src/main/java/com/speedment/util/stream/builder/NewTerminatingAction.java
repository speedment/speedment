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
package com.speedment.util.stream.builder;

import com.speedment.util.stream.builder.action.Action;
import java.util.LinkedList;
import java.util.function.Function;

/**
 *
 * @author pemi
 * @param <T>
 */
public abstract class NewTerminatingAction<T> implements Function<LinkedList<Action<?, ?>>, T> {
    
    private LinkedList<Action<?, ?>> actions;

    public NewTerminatingAction(LinkedList<Action<?, ?>> actions) {
        this.actions = actions;
    }
    
    protected LinkedList<Action<?, ?>> getActions() {
        return actions;
    }
}
