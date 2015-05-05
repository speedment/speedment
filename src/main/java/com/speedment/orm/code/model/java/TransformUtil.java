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
package com.speedment.orm.code.model.java;

import static com.speedment.codegen.Formatting.packageName;
import static com.speedment.codegen.Formatting.shortName;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Meta;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Type;
import com.speedment.orm.code.model.java.transform.TableToType;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Table;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public final class TransformUtil {
    
    private TransformUtil() {}
    
    public static Type getImplType(Type entityType) {
        final String pack = packageName(entityType.getName()).get();
        final String name = shortName(entityType.getName());
        
        return Type.of(
            pack + ".impl." + name + "Impl"
        );
    }
    
    public static Type typeUsing(Generator gen, Column column, final Class<? extends TableToType> transformer) {
        return typeUsing(gen, column.getParent().get(), transformer);
    }
    
    public static Type typeUsing(Generator gen, Table table, final Class<? extends TableToType> transformer) {
        List<Meta<Table, Type>> results = gen.metaOn(table, Type.class).collect(toList());
        
        return results.stream()
            .filter(m -> m.getTransform().is(transformer))
            .map(m -> m.getResult())
            .findAny()
            .get();
    }
    
    public static void importType(Generator gen, Import toImport) {
        gen.getRenderStack().fromBottom(ImportDelegator.class).findAny().get().add(toImport);
    }
}