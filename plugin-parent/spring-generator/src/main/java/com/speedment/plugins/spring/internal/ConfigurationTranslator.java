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
package com.speedment.plugins.spring.internal;

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import com.speedment.generator.translator.AbstractJavaClassTranslator;
import com.speedment.runtime.config.Project;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class ConfigurationTranslator extends AbstractJavaClassTranslator<Project, Class> {

    public ConfigurationTranslator(Project document) {
        super(document, Class::of);
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().typeName() + "Configuration";
    }
    
    @Override
    protected String getJavadocRepresentText() {
        return "The spring configuration file";
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, project) -> {
                clazz.public_();
                clazz.add(AnnotationUsage.of(Configuration.class));
                clazz.setSupertype(SimpleType.create(getSupport().basePackageName() + ".generated.Generated" + getSupport().typeName() + "Configuration"));
            }).build();
    }
}