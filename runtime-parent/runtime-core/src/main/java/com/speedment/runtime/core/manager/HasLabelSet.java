package com.speedment.runtime.core.manager;

@FunctionalInterface
public interface HasLabelSet<ENTITY> {
    boolean contains(String id);
}
