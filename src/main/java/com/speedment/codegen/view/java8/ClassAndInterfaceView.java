package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import static com.speedment.codegen.CodeUtil.EMPTY;
import static com.speedment.codegen.CodeUtil.SPACE;
import com.speedment.codegen.model.ClassAndInterfaceBase;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.modifier.Modifier_;
import com.speedment.codegen.view.CodeView;
import com.speedment.util.$;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
			(v) -> new $(v.name().toLowerCase(), SPACE)
		));
	}
	
	public CharSequence renderPackage(CodeGenerator cg, Model model) {
		return cg.on(model.getPackage());
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
		return models.stream()
			.map((m) -> cg.on(m))
			.collect(Collectors.joining(delimiter, pre, suf));
	}
	
	public CharSequence renderModifiers(Model model, CodeGenerator cg, CharSequence delimiter) {
		return model.getModifiers().stream()
			.map((m) -> modifierTexts.get(m))
			.collect(Collectors.joining(delimiter));
	}
}
