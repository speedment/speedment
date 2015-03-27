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

import com.speedment.orm.code.model.java.BaseEntityAndManagerTranslator;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import com.speedment.orm.code.model.java.manager.EntityManagerTranslator;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.ForeignKeyColumn;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.ManagerComponent;
import com.speedment.util.Pluralis;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends BaseEntityAndManagerTranslator<Interface> {

    public EntityTranslator(CodeGenerator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Interface make(File file) {
        return new InterfaceBuilder(ENTITY.getName())
                .addColumnConsumer((i, c) -> {
                    i.add(Method.of(GETTER_METHOD_PREFIX + typeName(c), Type.of(c.getMapping())));
                })
                .addForeignKeyReferencesThisTableConsumer((i, fk) -> {
                    file.add(Import.of(Type.of(Platform.class)));
                    file.add(Import.of(Type.of(ManagerComponent.class)));
                    file.add(Import.of(Type.of(Objects.class)));

                    final ForeignKeyColumn fkc = fk.stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("FK " + fk.getName() + " does not have a ForeignKeyColumn"));
                    final Column referencingColumn = fkc.getColumn();
                    final Table referencingTable = referencingColumn.ancestor(Table.class).get();
                    final EntityManagerTranslator emt = new EntityManagerTranslator(getCodeGenerator(), referencingTable);
                    
                    final Type returnType = Type.of(Stream.class).add(emt.GENERIC_OF_ENTITY);
                    final Method method = Method.of(Pluralis.INSTANCE.pluralizeJavaIdentifier(variableName(referencingTable)) + "By" + typeName(fkc), returnType)
                    .default_()
                    .add("return Platform.get().get(ManagerComponent.class)")
                    .add("        .manager(" + managerTypeName(referencingTable) + ".class)")
                    .add("        .stream().filter(" + variableName(fkc.getForeignColumn()) + " -> Objects.equals(this." + GETTER_METHOD_PREFIX + typeName(fkc.getForeignColumn()) + "(), " + variableName(fkc.getForeignColumn()) + "." + GETTER_METHOD_PREFIX + typeName(fkc.getForeignColumn()) + "()));");
                    i.add(method);
                })
                .build()
                .public_();

    }

    /*
    
     default Stream<Carrot> findCarrots() {
     return Platform.get().get(ManagerComponent.class)
     .manager(CarrotManager.class)
     .stream().filter(carrot -> Objects.equals(this.getId(), carrot.getHare()));
     }
    
     */
    @Override
    protected String getJavadocRepresentText() {
        return "An interface";
    }

    @Override
    protected String getFileName() {
        return ENTITY.getName();
    }

}
