package com.speedment.generator.translator.component.function;

import com.speedment.common.codegen.model.Interface;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;

/**
 * Specialization of {@link GeneratorFunction} that produces a CodeGen
 * {@code Interface}.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
@FunctionalInterface
public interface GenerateInterface<DOC extends HasName & HasMainInterface>
extends GeneratorFunction<DOC, Interface> {}
