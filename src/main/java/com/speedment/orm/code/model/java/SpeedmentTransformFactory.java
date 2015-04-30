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

import com.speedment.codegen.java.JavaTransformFactory;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Type;
import com.speedment.orm.code.model.java.transform.ColumnToEntityFieldMember;
import com.speedment.orm.code.model.java.transform.TableToBuilderType;
import com.speedment.orm.code.model.java.transform.TableToEntityFieldFile;
import com.speedment.orm.code.model.java.transform.TableToEntityType;
import com.speedment.orm.code.model.java.transform.TableToFieldType;
import com.speedment.orm.code.model.java.transform.TableToManagerType;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 */
public class SpeedmentTransformFactory extends JavaTransformFactory {

    public SpeedmentTransformFactory() {
        super(SpeedmentTransformFactory.class.getSimpleName());
        install(Table.class, Type.class, TableToBuilderType.class);
        install(Table.class, Type.class, TableToEntityType.class);
        install(Table.class, Type.class, TableToFieldType.class);
        install(Table.class, Type.class, TableToManagerType.class);
        install(Column.class, Field.class, ColumnToEntityFieldMember.class);
        install(Table.class, File.class, TableToEntityFieldFile.class);
    }
    
}