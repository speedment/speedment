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
package com.speedment.codegen.examples;

import com.speedment.codegen.Formatting;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.java.JavaGenerator;
import com.speedment.codegen.lang.controller.AutoImports;
import com.speedment.codegen.lang.controller.SetGetAdd;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.constants.DefaultType;
import com.speedment.codegen.lang.models.values.NumberValue;
import com.speedment.codegen.lang.models.values.ReferenceValue;
import java.util.ArrayList;

/**
 *
 * @author Emil Forslund
 */
public class SetGetExample {
	public static void main(String... params) {
		final CodeGenerator cg = new JavaGenerator();
		Formatting.tab("    ");
		
		final File f = File.of("org/example/codegen/Game.java")
            .add(Import.of(Type.of(ArrayList.class)))
			.add(Class.of("Game").public_()
				.add(Field.of("width", DefaultType.INT_PRIMITIVE)
                    .set(new NumberValue(640))
                )
				.add(Field.of("height", DefaultType.INT_PRIMITIVE)
                    .set(new NumberValue(480))
                )
				.add(Field.of("entities", DefaultType.list(
					Type.of("org.example.codegen.Entity")
				)).set(new ReferenceValue("new ArrayList<>()"))
                )
				.call(new SetGetAdd())
			).call(new AutoImports(cg.getDependencyMgr()))
		;
		
		System.out.println(cg.on(f).get());
	}
}