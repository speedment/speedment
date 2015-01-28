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

import com.speedment.codegen.CodeUtil;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.model.statement.Statement_;
import com.speedment.codegen.model.class_.Class_;
import static com.speedment.codegen.CodeUtil.*;
import com.speedment.codegen.model.type.ScalarType_;
import com.speedment.codegen.model.parameter.Parameter_;
import com.speedment.util.$;

/**
 *
 * @author pemi
 */
public class AccessorImplementer implements Controller<Class_> {
	
	private final static CharSequence 
		GET_STRING = "get",
		SET_STRING = "set",
		RETURN_STRING = "return ",
		THIS_STRING = "this",
		ASSIGNMENT_STRING = " = ";

    @Override
    public void accept(Class_ class_) {
        class_.getFields().forEach((f) -> generateAccessors(class_, f));
    }

    protected void generateAccessors(final Class_ class_, final Field_ field_) {
        generateGetter(class_, field_);
        generateSetter(class_, field_);
    }

    protected void generateGetter(final Class_ class_, final Field_ field_) {
		class_.add(new Method_(
			field_.getType(), 
			new $(GET_STRING, ucfirst(field_.getName()))
		).public_()
			.add(Statement_.of(
				new $(RETURN_STRING, field_.getName(), SC)
			))
		);
    }

    protected void generateSetter(final Class_ class_, final Field_ field_) {
		class_.add(new Method_(
			new ScalarType_(CodeUtil.flattenName(class_)), 
			new $(SET_STRING, ucfirst(field_.getName()))
		).public_()
			.add(new Parameter_(
				field_.getType(), 
				field_.getName()
			)).add(Statement_.of(
				new $(THIS_STRING, DOT, field_.getName(), ASSIGNMENT_STRING, field_.getName(), SC)
			)).add(Statement_.of(
				new $(RETURN_STRING, THIS_STRING, SC)
			))
		);
    }
}
