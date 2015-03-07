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
package com.speedment.orm.code.model.java.entity;

import com.speedment.codegen.lang.models.AnnotationUsage;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultType.VOID;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends BaseEntityTranslator<Interface> {



    public EntityTranslator(Table configEntity) {
        super(configEntity);
    }

//    protected Interface make3() {
//        return Interface.of(INTERFACE.getType().getName())
//                .call(new GenerateMgrClass(table));
//    }
//    class GenerateMgrClass<T> extends Consumer<ClassOrInterface<T>> {
//
//        public GenerateMgrClass(Table table) {
//
//        }
//
//        public void accept(T classOrInterface) {
//
//        }
//    }
    @Override
    protected Interface make() {
        return new InterfaceBuilder(INTERFACE.getType().getName())
                .addColumnConsumer((i, c) -> {
                    i.add(Method.of(GETTER_METHOD_PREFIX + typeName(c), Type.of(c.getMapping())));
                })
                .build()
                .public_()
                .add(AnnotationUsage.of(Type.of(Api.class)))
                .add(bean())
                .add(builder());
    }

    private Interface bean() {
        return new InterfaceBuilder("Bean")
                .addColumnConsumer((i, c) -> {
                    i.add(Method.of(SETTER_METHOD_PREFIX + typeName(c), VOID).add(fieldFor(c)));
                })
                .build()
                .public_()
                .add(INTERFACE.getType());
    }

    private Interface builder() {
        return new InterfaceBuilder("Builder")
                .addColumnConsumer((i, c) -> {
                    i.add(Method.of(BUILDER_METHOD_PREFIX + typeName(c), Type.of("Builder")).add(fieldFor(c)));
                })
                .build()
                .public_()
                .add(INTERFACE.getType())
                .add(Type.of(Buildable.class)
                        .add(new GenericImpl(INTERFACE.getType())));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An interface";
    }

    @Override
    protected String getFileName() {
        return INTERFACE.getType().getName();
    }

}
