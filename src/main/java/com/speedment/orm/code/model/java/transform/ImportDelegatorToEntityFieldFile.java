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
package com.speedment.orm.code.model.java.transform;

import static com.speedment.codegen.Formatting.classToJavaFileName;
import static com.speedment.codegen.Formatting.shortName;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Meta;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.controller.AutoImports;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Constructor;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.GENERATED;
import com.speedment.codegen.lang.models.values.TextValue;
import com.speedment.orm.code.model.java.ImportDelegator;
import static com.speedment.orm.code.model.java.TransformUtil.typeUsing;
import com.speedment.orm.config.model.Column;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public class ImportDelegatorToEntityFieldFile implements Transform<ImportDelegator, File> {

    @Override
    public Optional<File> transform(Generator gen, ImportDelegator model) {
        final Type fieldType = typeUsing(gen, model.getTable(), TableToFieldType.class);
        
        final List<Column> cols = model.getTable().streamOf(Column.class).collect(toList());
        final File file = File.of(
            classToJavaFileName(
                typeUsing(gen, model.getTable(), TableToFieldType.class).getName()
            )
        );
        
        model.setImportAdder(file::add);
        
        return Optional.of(
            file.add(Class.of(shortName(fieldType.getName()))
                .add(GENERATED.set(new TextValue("Speedment")))
                .public_().final_()
                .add(Constructor.of().private_())
                .addAllFields(
                    gen.metaOn(
                        cols,
                        Field.class,
                        ColumnToEntityFieldMember.class
                    ).map(Meta::getResult).collect(toList())
                )
            ).call(new AutoImports(gen.getDependencyMgr()))
        );
    }
}
