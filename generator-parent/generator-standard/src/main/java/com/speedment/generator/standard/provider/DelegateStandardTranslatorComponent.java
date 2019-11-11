package com.speedment.generator.standard.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.generator.standard.internal.StandardTranslatorComponent;
import com.speedment.generator.translator.component.CodeGenerationComponent;

import static com.speedment.common.injector.State.INITIALIZED;

public final class DelegateStandardTranslatorComponent {

    private final StandardTranslatorComponent inner;

    public DelegateStandardTranslatorComponent() {
        this.inner = new StandardTranslatorComponent();
    }

    @ExecuteBefore(INITIALIZED)
    public void installTranslators(CodeGenerationComponent codeGen) {
        inner.installTranslators(codeGen);
    }
}
