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
package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import static com.speedment.codegen.CodeUtil.*;
import com.speedment.codegen.model.class_.ClassAndInterfaceBase;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.modifier.Modifier_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import com.speedment.util.StreamUtil;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Duncan
 * @param <Model>
 * @param <Modifier>
 */
public abstract class ClassAndInterfaceView<Modifier extends Enum<Modifier> & Modifier_<Modifier>, Model extends ClassAndInterfaceBase<Model, Modifier>> extends CodeView<Model> {
	
	public final static String 
		EXTENDS_STRING = "extends ",
		COMMA_STRING = ", ";
	
	private final Map<Modifier, CharSequence> modifierTexts;
	
	public ClassAndInterfaceView(Class<? extends Modifier_> modifierClass, Modifier_[] enumConstants) {
		modifierTexts = new EnumMap(modifierClass);

		Stream.of(enumConstants).collect(Collectors.toMap(
			Function.identity(),
			(v) -> new $(v.name().toLowerCase())
		)).forEach((k,v) -> {
			modifierTexts.put((Modifier) k, v);
		});
	}
	
	public CharSequence renderPackage(CodeGenerator cg, Model model) {
		Optional<CharSequence> o = cg.on(model.getPackage());
		return o.isPresent() ? o.get() : EMPTY;
	}
	
	public CharSequence renderDependencies(CodeGenerator cg, Model model) {
		return model.getDependencies().stream()
			.map(d -> cg.on(d))
			.flatMap(StreamUtil::mandatory)
			.sorted()
			.collect(Collectors.joining(nl(), EMPTY, dnl()));
	}
	
	public CharSequence renderIf(Model model, Modifier condition, CharSequence text) {
		return model.is(condition) ? text : EMPTY;
	}
	
	public CharSequence renderName(Model model) {
		return model.getName();
	}
	
	public <T extends CodeModel> CharSequence renderList(List<T> models, CodeGenerator cg, CharSequence delimiter) {
		return renderList(models, cg, delimiter, EMPTY, EMPTY);
	}
	
	public <T extends CodeModel> CharSequence renderList(List<T> models, CodeGenerator cg, CharSequence delimiter, CharSequence pre, CharSequence suf) {
		if (models.isEmpty()) {
			return EMPTY;
		} else {
			return cg.onEach(models).collect(Collectors.joining(delimiter, pre, suf));
		}
	}
	
	public CharSequence renderModifiers(Model model, CodeGenerator cg, CharSequence delimiter) {
		return model.getModifiers().stream()
			.map((m) -> modifierTexts.get(m))
			.collect(Collectors.joining(delimiter, EMPTY, delimiter));
	}
}
