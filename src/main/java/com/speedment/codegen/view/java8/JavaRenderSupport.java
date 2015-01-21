package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import static com.speedment.codegen.CodeUtil.EMPTY;
import static com.speedment.codegen.CodeUtil.SC;
import com.speedment.codegen.model.CodeModel;
import static com.speedment.codegen.model.CodeModel.Type.PACKAGE;
import com.speedment.codegen.model.Interface_;
import com.speedment.codegen.model.Package_;
import com.speedment.codegen.model.modifier.ClassModifier_;
import com.speedment.codegen.model.modifier.InterfaceModifier_;
import static com.speedment.codegen.model.modifier.InterfaceModifier_.ABSTRACT;
import static com.speedment.codegen.model.modifier.InterfaceModifier_.PRIVATE;
import static com.speedment.codegen.model.modifier.InterfaceModifier_.PROTECTED;
import static com.speedment.codegen.model.modifier.InterfaceModifier_.PUBLIC;
import static com.speedment.codegen.model.modifier.InterfaceModifier_.STATIC;
import static com.speedment.codegen.model.modifier.InterfaceModifier_.STRICTFP;
import com.speedment.util.$;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Duncan
 */
public class JavaRenderSupport {
	public final static String 
		PACKAGE_STRING = "package ",
		EXTENDS_STRING = "extends ",
		COMMA_STRING = ", ";
	
	private final static Map<InterfaceModifier_, CharSequence> interfaceModifierTexts = new EnumMap<>(InterfaceModifier_.class);
	
	static {
		interfaceModifierTexts.put(PRIVATE, "private ");
		interfaceModifierTexts.put(PROTECTED, "protected ");
		interfaceModifierTexts.put(PUBLIC, "public ");
		interfaceModifierTexts.put(ABSTRACT, "abstract ");
		interfaceModifierTexts.put(STATIC, "static ");
		interfaceModifierTexts.put(STRICTFP, "strict ");
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
		return interf.getInterfaceModifiers().stream()
			.map((m) -> interfaceModifierTexts.get(m))
			.collect(Collectors.joining(delimiter));
	}
}
