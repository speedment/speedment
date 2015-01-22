package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.view.CodeView;
import static com.speedment.codegen.CodeUtil.*;
import com.speedment.codegen.model.modifier.Modifier_;
import java.util.stream.Collectors;
import com.speedment.util.$;

/**
 *
 * @author Duncan
 */
public class JavaMethodView extends CodeView<Method_> {

    private final static CharSequence PUBLIC = "public ",
            PROTECTED = "protected ",
            PRIVATE = "private ",
            FINAL = "final ",
            STATIC = "static ",
            COMMA = ", ";

    protected CharSequence render(Modifier_ m) {
        return m.name().toLowerCase();
    }

    @Override
    public CharSequence render(CodeGenerator renderer, Method_ method) {
        return new $(method.getModifiers().stream().map(this::render).collect(Collectors.joining(" ")),
                //			method.isPublic_() ? PUBLIC :
                //			method.isProtected_() ? PROTECTED :
                //			method.isPrivate_() ? PRIVATE : EMPTY, 
                //			method.isFinal_() ? FINAL : EMPTY,
                //			method.isStatic_() ? STATIC : EMPTY,
                method.getName_(), PS,
                method.getParameters().stream()
                .map((param) -> renderer.on(param))
                .collect(Collectors.joining(COMMA)),
                PE, renderer.on(method.getBlock_())
        );
    }
}
