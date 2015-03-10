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

import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.GENERATED;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends BaseEntityTranslator<Interface> {

    
//    private final String INTERFACE_NAME = Formatting.shortName(INTERFACE.getType().getName()),
//        BEAN_NAME = INTERFACE_NAME + "Bean",
//        BUILDER_NAME = Formatting.shortName(BUILDER.getType().getName()),
//        PERSISTER_NAME = Formatting.shortName(PERSISTER.getType().getName()),
//        CONFIG_NAME = Formatting.shortName(CONFIG.getType().getName());
    
//    private Generic genericOfSelf(Type type) {
//        return Generic.of().setLowerBound("T").add(type.copy().add(Generic.of().setLowerBound("T")));
//    }

    public EntityTranslator(CodeGenerator cg, Table configEntity) {
        super(cg, configEntity);
    }

//    @Override
//    public File get() {
//        final File file = super.get();
//        file.add(bean());
//        return file;
//    }

    @Override
    protected Interface make(File file) {
        return new InterfaceBuilder(ENTITY.getName())
            .addColumnConsumer((i, c) -> {
                i.add(Method.of(GETTER_METHOD_PREFIX + typeName(c), Type.of(c.getMapping())));
            })
            .build()
            .public_();
//            .add(AnnotationUsage.of(Type.of(Api.class)).put("version", new NumberValue(0)))
//            .add(builder())
//            .add(persister())
//            .add(managerMethod(file))
//            .add(builderMethod(file))
//            .add(toBuilderMethod(file))
//            .add(persisterMethod(file))
//            .add(toPersisterMethod(file));
    }
//
//    private Method managerMethod(File file) {
//        file.add(Import.of(CONFIG.getType()));
//        return Method.of("manager", Type.of(Manager.class).add(GENERIC_OF_ENTITY)).static_()
//            .add("return " + CONFIG.getName() + ".INSTANCE.speedment.managerOf(" + ENTITY.getName() + ".class);");
//    }
//
//    private Method builderMethod(File file) {
//        return Method.of("builder", BUILDER.getType()).static_()
//            .add("return manager().builder(" + BUILDER.getName() + ".class);");
//    }
//
//    private Method toBuilderMethod(File file) {
//        return Method.of("toBuilder", BUILDER.getType()).default_()
//            .add("return manager().builderOf(" + BUILDER.getName() + ".class, this);");
//    }
//
//    private Method persisterMethod(File file) {
//        return Method.of("persister", PERSISTER.getType()).static_()
//            .add("return manager().persister(" + PERSISTER.getName() + ".class);");
//    }
//
//    private Method toPersisterMethod(File file) {
//        return Method.of("toPersister", PERSISTER.getType()).default_()
//            .add("return manager().persisterOf(" + PERSISTER.getName() + ".class, this);");
//    }

//    private Interface bean() {
//        return new InterfaceBuilder(BEAN.getName())
//            .addColumnConsumer((i, c) -> {
//                i.add(Method.of(SETTER_METHOD_PREFIX + typeName(c), Type.of("T")).add(fieldFor(c)));
//            })
//            .build()
//            .add(genericOfSelf(BEAN.getType()))
//            .add(INTERFACE.getType());
//    }
//
//    private Interface builder() {
//        return new InterfaceBuilder(BUILDER.getName())
//            .addColumnConsumer((i, c) -> {
//                i.add(Method.of(SETTER_METHOD_PREFIX + typeName(c), BUILDER.getType()).add(fieldFor(c)));
//            })
//            .build()
//            .add(ENTITY.getType())
////            .add(genericOfSelf(BUILDER.getType()))
////            .add(BEAN.getType().copy().add(Generic.of().setLowerBound("T")))
//            .add(Type.of(Buildable.class).add(GENERIC_OF_ENTITY.copy()));
//    }
//
//    private Interface persister() {
//        return new InterfaceBuilder(PERSISTER.getName())
//            .addColumnConsumer((i, c) -> {
//                i.add(Method.of(SETTER_METHOD_PREFIX + typeName(c), PERSISTER.getType()).add(fieldFor(c)));
//            })
//            .build()
//            .add(ENTITY.getType())
////            .add(genericOfSelf(PERSISTER.getType()))
////            .add(BEAN.getType().copy().add(Generic.of().setLowerBound("T")))
//            .add(Type.of(Persistable.class)
//                .add(GENERIC_OF_ENTITY.copy()));
//    }

    @Override
    protected String getJavadocRepresentText() {
        return "An interface";
    }

    @Override
    protected String getFileName() {
        return ENTITY.getName();
    }

}
