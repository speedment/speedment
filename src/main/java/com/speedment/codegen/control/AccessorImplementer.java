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
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author pemi
 */
public class AccessorImplementer implements Controller<Class_> {

    @Override
    public void apply(Class_ class_) {
        class_.getFields().forEach((f) -> generateAccessors(class_, f));
    }

    protected void generateAccessors(final Class_ class_, final Field_ field_) {
        generateGetter(class_, field_);
        generateSetter(class_, field_);
    }

    protected void generateGetter(final Class_ class_, final Field_ field_) {
        final Method_ method_ = new Method_(field_.getType_(), "get" + ucfirst(field_.getName_()));
        method_.add(new Statement_("return " + field_.getName_()));
        class_.add(method_);
    }

    protected void generateSetter(final Class_ class_, final Field_ field_) {
        final Method_ method_ = new Method_(field_.getType_(), "set" + ucfirst(field_.getName_())).add(new Field_(field_.getType_(), field_.getName_()));
        method_.add(new Statement_("this." + field_.getName_() + " = " + field_.getName_()));
        class_.add(method_);
    }
}
