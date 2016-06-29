/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.plugins.springgenerator.internal;

import static com.speedment.common.codegen.internal.model.constant.DefaultType.LONG_PRIMITIVE;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.list;
import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import com.speedment.common.codegen.internal.model.value.TextValue;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.TranslatorSupport;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.generator.util.JavaLanguageNamer;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class GeneratedControllerTranslator extends DefaultJavaClassTranslator<Table, Class> {

    private @Inject JavaLanguageNamer namer;
    
    public GeneratedControllerTranslator(Table document) {
        super(document, Class::of);
    }
    
    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryTable((clazz, table) -> {
                
                final Project project = getSupport().projectOrThrow();
                final TranslatorSupport<Project> projectSupport = new TranslatorSupport<>(namer, project);
                
                final Type appType = Type.of(
                    projectSupport.basePackageName() + "." +
                    projectSupport.typeName() + "Application"
                );
                
                clazz.public_().abstract_();
                
                clazz.add(Field.of("app", appType)
                    .protected_()
                    .add(AnnotationUsage.of(Type.of(Autowired.class)))
                );
                
                clazz.add(Field.of("manager", getSupport().managerType())
                    .protected_()
                    .add(AnnotationUsage.of(Type.of(Autowired.class)))
                );
                
                file.add(Import.of(Type.of(RequestMethod.class)).static_().setStaticMember("GET"));
                file.add(Import.of(Type.of(Collectors.class)).static_().setStaticMember("toList"));
                
                clazz.add(Method.of("get", list(getSupport().entityType()))
                    .public_()
                    .add(AnnotationUsage.of(Type.of(RequestMapping.class))
                        .put("value", new TextValue("/" + getSupport().variableName()))
                        .put("method", new ReferenceValue("GET"))
                    )
                    .add(Field.of("start", LONG_PRIMITIVE)
                        .add(AnnotationUsage.of(Type.of(RequestParam.class))
                            .set(new TextValue("start"))
                        )
                    )
                    .add(Field.of("limit", LONG_PRIMITIVE)
                        .add(AnnotationUsage.of(Type.of(RequestParam.class))
                            .set(new TextValue("limit"))
                        )
                    )
                    .add(
                        "return manager.stream()",
                        "    .skip(start)",
                        "    .limit(limit)",
                        "    .collect(toList());"
                    )
                );
            }).build();
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getSupport().typeName() + "Controller";
    }
    
    @Override
    protected String getJavadocRepresentText() {
        return "The default REST controller logic";
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
}