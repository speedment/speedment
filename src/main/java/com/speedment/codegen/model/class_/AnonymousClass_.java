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
package com.speedment.codegen.model.class_;

import com.speedment.codegen.model.modifier.ClassModifier_;
import com.speedment.codegen.model.package_.Package_;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author pemi
 */
public class AnonymousClass_ extends Class_ {

    @Override
    public boolean is(ClassModifier_ modifier) {
        return false;
    }

    @Override
    public Set<ClassModifier_> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public CharSequence getName() {
        return "";
    }

    @Override
    public AnonymousClass_ setName(CharSequence name) {
        throwUnsupportedOperationException("setName");
        return this;
    }

    @Override
    public AnonymousClass_ setPackage(Package_ pagage) {
        return throwUnsupportedOperationException("setName");
    }

    @Override
    public Package_ getPackage() {
        return throwUnsupportedOperationException("getPackage");
    }

    private static <T> T throwUnsupportedOperationException(final String msg) {
        throw new UnsupportedOperationException("The method " + msg + " is not supported by " + AnonymousClass_.class.getName());
    }

}
