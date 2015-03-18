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
package com.speedment.codegen.lang.models.constants;

import com.speedment.codegen.lang.models.AnnotationUsage;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultValue.string;
import com.speedment.codegen.lang.models.implementation.AnnotationUsageImpl.AnnotationUsageConst;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Native;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.annotation.Generated;

/**
 *
 * @author Emil Forslund
 */
public abstract class DefaultAnnotationUsage {
    private DefaultAnnotationUsage() {}
    
    public final static AnnotationUsage
		OVERRIDE   = new AnnotationUsageConst(Type.of(Override.class)),
		DOCUMENTED = new AnnotationUsageConst(Type.of(Documented.class)),
		INHERITED  = new AnnotationUsageConst(Type.of(Inherited.class)),
		NATIVE     = new AnnotationUsageConst(Type.of(Native.class)),
		REPEATABLE = new AnnotationUsageConst(Type.of(Repeatable.class)),
		RETENTION  = new AnnotationUsageConst(Type.of(Retention.class)),
		TARGET     = new AnnotationUsageConst(Type.of(Target.class)),
		GENERATED  = new AnnotationUsageConst(Type.of(Generated.class)),
        DEPRECATED = new AnnotationUsageConst(Type.of(Deprecated.class)),
        SUPPRESS_WARNINGS_UNCHECKED = new AnnotationUsageConst(
            Type.of(SuppressWarnings.class), string("unchecked")
        );
}