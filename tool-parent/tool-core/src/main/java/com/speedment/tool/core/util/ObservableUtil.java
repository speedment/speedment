/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.util;


import javafx.beans.InvalidationListener;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;

/**
 * Utility methods for binding observable properties that can't easily be
 * matched otherwise.
 *
 * @author Emil Forslund
 */
public final class ObservableUtil {

    public static void bind(ObjectProperty<Integer> property, IntegerBinding binding) {
        property.bind(new ObservableValue<Integer>() {
            @Override
            public void addListener(ChangeListener<? super Integer> listener) {
                binding.addListener(numberListenerOf(listener));
            }

            @Override
            public void removeListener(ChangeListener<? super Integer> listener) {
                binding.removeListener(numberListenerOf(listener));
            }

            @Override
            public Integer getValue() {
                return binding.get();
            }

            @Override
            public void addListener(InvalidationListener listener) {
                binding.addListener(listener);
            }

            @Override
            public void removeListener(InvalidationListener listener) {
                binding.removeListener(listener);
            }

            private ChangeListener<? super Number> numberListenerOf(ChangeListener<? super Integer> listener) {
                @SuppressWarnings("unchecked")
                final ChangeListener<? super Number> result = (ChangeListener<? super Number>) listener;
                return result;
            }

        });
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ObservableUtil() {
        instanceNotAllowed(getClass());
    }
}
