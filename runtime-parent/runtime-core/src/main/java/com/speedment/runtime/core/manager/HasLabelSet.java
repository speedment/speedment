package com.speedment.runtime.core.manager;

import java.util.function.Predicate;

@FunctionalInterface
public interface HasLabelSet<ENTITY> extends Predicate<String> {
}
