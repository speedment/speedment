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
package com.speedment.orm.core.manager.sql.generator;

import com.speedment.codegen.base.DefaultTransformFactory;
import com.speedment.orm.field.BinaryPredicateBuilder;
import com.speedment.orm.field.UnaryPredicateBuilder;
import com.speedment.orm.field.reference.string.StringBinaryPredicateBuilder;

/**
 *
 * @author Emil Forslund
 */
public class SQLTransformFactory extends DefaultTransformFactory {

    public SQLTransformFactory() {
        super(SQLTransformFactory.class.getSimpleName());
        install(UnaryPredicateBuilder.class, UnaryPredicateBuilderView.class);
		install(BinaryPredicateBuilder.class, BinaryPredicateBuilderView.class);
		install(StringBinaryPredicateBuilder.class, StringBinaryPredicateBuilderView.class);
    }
}
