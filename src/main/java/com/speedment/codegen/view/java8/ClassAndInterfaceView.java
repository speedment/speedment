package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import static com.speedment.codegen.CodeUtil.EMPTY;
import static com.speedment.codegen.CodeUtil.SC;
import static com.speedment.codegen.CodeUtil.SPACE;
import com.speedment.codegen.model.ClassAndInterfaceBase;
import com.speedment.codegen.model.CodeModel;
import static com.speedment.codegen.model.CodeModel.Type.PACKAGE;
import com.speedment.codegen.model.Interface_;
import com.speedment.codegen.model.Package_;
import com.speedment.codegen.model.modifier.InterfaceModifier_;
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
 */
public class ClassAndInterfaceView<Model extends ClassAndInterfaceBase> {
	public final static String 
		PACKAGE_STRING = "package ",
		EXTENDS_STRING = "extends ",
		COMMA_STRING = ", ";
	
	private final static Map<InterfaceModifier_, CharSequence> interfaceModifierTexts = new EnumMap<>(InterfaceModifier_.class);
	
	static {
		Stream.of(InterfaceModifier_.values()).collect(Collectors.toMap(
			Function.identity(), v -> new $(v.name().toLowerCase(), SPACE)
		));
	}
	
	public static CharSequence renderPackage(CodeGenerator cg, Interface_ interf) {
		return new $(PACKAGE_STRING, ((Package_) cg.last(PACKAGE)).getName_(), SC);
	}
	
	public static CharSequence renderIf(Interface_ interf, InterfaceModifier_ condition, CharSequence text) {
		return interf.is(condition) ? text : EMPTY;
	}
	
	public static CharSequence renderName(Interface_ clazz) {
		return clazz.getName();
	}
	
	public static <T extends CodeModel> CharSequence renderList(List<T> models, CodeGenerator cg, CharSequence delimiter) {
		return renderList(models, cg, delimiter, EMPTY, EMPTY);
	}
	
	public static <T extends CodeModel> CharSequence renderList(List<T> models, CodeGenerator cg, CharSequence delimiter, CharSequence pre, CharSequence suf) {
		return models.stream()
			.map((m) -> cg.on(m))
			.collect(Collectors.joining(delimiter, pre, suf));
	}
	
	public static CharSequence renderModifiers(Interface_ interf, CodeGenerator cg, CharSequence delimiter) {
		return interf.getModifiers().stream()
			.map((m) -> interfaceModifierTexts.get(m))
			.collect(Collectors.joining(delimiter));
	}
}
