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
package com.speedment.codegen.control;

import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.model.Statement_;
import com.speedment.codegen.model.class_.Class_;

/**
 *
 * @author pemi
 */
public class AccessorImplementer implements Controller {

    private final Class_ class_;

    public AccessorImplementer(final Class_ class_) {
        this.class_ = class_;
    }

    @Override
    public void apply() {
        class_.getFields().forEach((f) -> generateAccessors(f));
    }

    protected void generateAccessors(final Field_ field_) {
        generateGetter(field_);
        generateSetter(field_);
    }

    protected void generateGetter(final Field_ field_) {
        final Method_ method_ = new Method_(field_.getType_(), "get" + firstCharCapital(field_.getName_()));
        method_.add(new Statement_("return " + field_.getName_()));
        class_.add(method_);
    }

    protected void generateSetter(final Field_ field_) {
        final Method_ method_ = new Method_(field_.getType_(), "set" + firstCharCapital(field_.getName_())).add(new Field_(field_.getType_(), field_.getName_()));
        method_.add(new Statement_("this." + field_.getName_() + " = " + field_.getName_()));
        class_.add(method_);
    }

    private String firstCharCapital(CharSequence s) {
        return new StringBuilder().append(Character.toUpperCase(s.charAt(0))).append(s.subSequence(1, s.length())).toString();
    }

}
