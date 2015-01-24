package com.speedment.codegen.model.dependency_;

import java.util.Set;

/**
 *
 * @author Duncan
 */
public interface Dependable {
	Set<Dependency_> getDependencies();
    Dependable add(final Dependency_ dep);
}
