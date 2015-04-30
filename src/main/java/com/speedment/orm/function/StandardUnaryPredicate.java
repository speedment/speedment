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
package com.speedment.orm.function;

import java.util.function.Predicate;

/**
 *
 * @author pemi
 */
@Deprecated
public enum StandardUnaryPredicate implements Predicate<Object> {

    IS_NULL {

                @Override
                public boolean test(Object t) {
                    return t == null;
                }

            },
    IS_NOT_NULL {

                @Override
                public boolean test(Object t) {
                    return t != null;
                }

            },
    TRUE {

                @Override
                public boolean test(Object t) {
                    return true;
                }

            },
    FALSE {

                @Override
                public boolean test(Object t) {
                    return false;
                }

            }

}
