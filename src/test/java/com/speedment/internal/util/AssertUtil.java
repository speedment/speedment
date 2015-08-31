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
package com.speedment.internal.util;

/**
 *
 * @author pemi
 */
public class AssertUtil {

    public static AssertThrown assertThrown(Runnable r) {
        return new AssertThrown(r);
    }

    public static class AssertThrown {

        private final Runnable runnable;
        private Class<? extends Exception> expecedClass;

        public AssertThrown(Runnable r) {
            this.runnable = r;
        }

        public AssertThrown isInstanceOf(Class<? extends Exception> expectedClass) {
            this.expecedClass = expectedClass;
            return this;
        }

        public void test() {
            if (!eval()) {
                org.junit.Assert.fail("Throwable not as expected");
            }
        }

        private boolean eval() {
            try {
                runnable.run();
            } catch (Exception e) {
                if (expecedClass == null) {
                    return true;
                } else {
                    return e.getClass().isAssignableFrom(expecedClass);
                }
            }
            return false;
        }

    }

}
