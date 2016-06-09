package com.speedment.common.injector.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 */
public final class CyclicReferenceException extends RuntimeException {

    private static final long serialVersionUID = -5890725902790625145L;
    
    private final List<Class<?>> stack;
    
    public CyclicReferenceException(Class<?> referencedClass) {
        this.stack = Collections.singletonList(referencedClass);
    }
    
    public CyclicReferenceException(Class<?> referencingClass, CyclicReferenceException cause) {
        this.stack = new ArrayList<>(cause.stack);
        this.stack.add(0, referencingClass);
    }

    @Override
    public String getMessage() {
        return "Cyclic dependency prevented class from being injected. Stack: "
            + stack.stream().map(Class::getSimpleName).collect(toList()) + ".";
    }
}