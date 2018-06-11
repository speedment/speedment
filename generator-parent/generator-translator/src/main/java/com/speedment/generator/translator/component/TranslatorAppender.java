package com.speedment.generator.translator.component;

import com.speedment.generator.translator.component.function.GenerateClass;
import com.speedment.generator.translator.component.function.GenerateEnum;
import com.speedment.generator.translator.component.function.GenerateInterface;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;

/**
 * Appender to the {@link CodeGenerationComponent} used to configure new
 * translators.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface TranslatorAppender<DOC extends HasName & HasMainInterface> {

    /**
     * Creates a new dynamic code translator and adds it to this component. The
     * created translator will not listen to any decorators.
     *
     * @param creator  the CodeGen model creator
     * @return         this component
     * @since  3.1.4
     */
    CodeGenerationComponent newClass(GenerateClass<DOC> creator);

    /**
     * Creates a new dynamic code translator and adds it to this component. The
     * created translator will not listen to any decorators.
     *
     * @param creator  the CodeGen model creator
     * @return         this component
     * @since  3.1.4
     */
    CodeGenerationComponent newEnum(GenerateEnum<DOC> creator);

    /**
     * Creates a new dynamic code translator and adds it to this component. The
     * created translator will not listen to any decorators.
     *
     * @param creator  the CodeGen model creator
     * @return         this component
     * @since  3.1.4
     */
    CodeGenerationComponent newInterface(GenerateInterface<DOC> creator);

}
