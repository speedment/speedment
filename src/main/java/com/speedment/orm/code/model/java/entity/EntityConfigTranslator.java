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

import com.speedment.codegen.Formatting;
import static com.speedment.codegen.Formatting.block;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.Enum;
import com.speedment.codegen.lang.models.EnumConstant;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultType.BOOLEAN_PRIMITIVE;
import static com.speedment.codegen.lang.models.constants.DefaultType.VOID;
import com.speedment.codegen.lang.models.values.BooleanValue;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.platform.Speedment;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
public class EntityConfigTranslator extends BaseEntityTranslator<Enum> {

    public EntityConfigTranslator(CodeGenerator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Enum make(File file) {
        final Supplier<Field> speedment = () -> Field.of("speedment", Type.of(Speedment.class));

        return Enum.of(Formatting.shortName(CONFIG.getType().getName()))
            .add(EnumConstant.of("INSTANCE"))
            .add(Field.of("running", BOOLEAN_PRIMITIVE).set(new BooleanValue(false)))
            .add(speedment.get())
            .add(Method.of("setSpeedment", VOID)
                .public_()
                .add(speedment.get())
                .add("if (running) " + block(
                        "throw new IllegalStateException(\"Can't change Speedment instance while running!\");"
                    )
                ).add("INSTANCE.speedment = speedment;")
            )
            .add(Method.of("setRunning", VOID)
                .public_()
                .add("running = true;"))
            .call(e -> file.add(Import.of(Type.of(IllegalStateException.class))));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A configuration";
    }

    @Override
    protected String getFileName() {
        return Formatting.shortName(CONFIG.getType().getName());
    }

}
