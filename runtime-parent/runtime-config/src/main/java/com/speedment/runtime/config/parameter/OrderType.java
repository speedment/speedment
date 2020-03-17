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
package com.speedment.runtime.config.parameter;



import java.util.function.Supplier;

/**
 *
 * @author  Per Minborg
 */

public enum OrderType {
    ASC {
        @Override
        public <T> T selectLazily(Supplier<T> ascAternative, Supplier<T> descAternative) {
            return ascAternative.get();
        }

        @Override
        public <T> T select(T ascAternative, T descAternative) {
            return ascAternative;
        }

        @Override
        public void selectRunnable(Runnable ascAternative, Runnable descAternative) {
            ascAternative.run();
        }

    }, DESC {
        @Override
        public <T> T selectLazily(Supplier<T> ascAternative, Supplier<T> descAternative) {
            return descAternative.get();
        }

        @Override
        public <T> T select(T ascAternative, T descAternative) {
            return descAternative;
        }

        @Override
        public void selectRunnable(Runnable ascAternative, Runnable descAternative) {
            descAternative.run();
        }

    }, NONE {
        @Override
        public <T> T selectLazily(Supplier<T> ascAternative, Supplier<T> descAternative) {
            return ascAternative.get();
        }

        @Override
        public <T> T select(T ascAternative, T descAternative) {
            return ascAternative;
        }

        @Override
        public void selectRunnable(Runnable ascAternative, Runnable descAternative) {
            ascAternative.run();
        }
    };

    public abstract <T> T selectLazily(Supplier<T> ascAternative, Supplier<T> descAternative);

    public abstract <T> T select(T ascAternative, T descAternative);

    public abstract void selectRunnable(Runnable ascAternative, Runnable descAternative);

}
