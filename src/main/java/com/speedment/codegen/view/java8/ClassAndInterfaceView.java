package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import static com.speedment.codegen.CodeUtil.EMPTY;
import static com.speedment.codegen.CodeUtil.SC;
import static com.speedment.codegen.CodeUtil.SPACE;
import static com.speedment.codegen.CodeUtil.dnl;
import static com.speedment.codegen.CodeUtil.looseBracketsIndent;
import static com.speedment.codegen.CodeUtil.nl;
import com.speedment.codegen.model.ClassAndInterfaceBase;
import com.speedment.codegen.model.CodeModel;
import static com.speedment.codegen.model.CodeModel.Type.PACKAGE;
import com.speedment.codegen.model.Interface_;
import com.speedment.codegen.model.Package_;
import com.speedment.codegen.model.modifier.InterfaceModifier_;
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
public class ClassAndInterfaceView<Model extends ClassAndInterfaceBase<Model, Modifier>, Modifier extends Enum<Modifier> & Modifier_<Modifier>> extends CodeView<Model> {
	public final static String 
		PACKAGE_STRING = "package ",
		EXTENDS_STRING = "extends ",
		COMMA_STRING = ", ";
	
	private final Map<Modifier, CharSequence> modifierTexts;
	
	public ClassAndInterfaceView(Class<Modifier> modifierClass) {
		modifierTexts = new EnumMap<>(modifierClass);

		Stream.of(modifierClass.getEnumConstants()).collect(Collectors.toMap(
			Function.identity(), v -> new $(v.name(), SPACE)
		));
	}
	
	public CharSequence renderPackage(CodeGenerator cg, Model interf) {
		return new $(PACKAGE_STRING, ((Package_) cg.last(PACKAGE)).getName_(), SC);
	}
	
	public CharSequence renderIf(Model interf, Modifier_<Model> condition, CharSequence text) {
		return interf.is(condition) ? text : EMPTY;
	}
	
	public CharSequence renderName(Model clazz) {
		return clazz.getName();
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

	@Override
	public CharSequence render(CodeGenerator renderer, Model model) {
		return new $(
			renderPackage(renderer, model), dnl(),
			renderModifiers(model, renderer, SPACE),
			renderName(model), SPACE,
			renderList(model.getInterfaces(), renderer, COMMA_STRING, EXTENDS_STRING, SPACE),
			looseBracketsIndent(new $(
				renderList(model.getFields(), renderer, nl()), dnl(),
				renderList(model.getMethods(), renderer, dnl())
			))
		);
	}
}
