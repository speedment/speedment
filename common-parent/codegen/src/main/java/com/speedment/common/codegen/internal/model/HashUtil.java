package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.trait.HasParent;

public final class HashUtil {

    private HashUtil() {}

    public static int identityHashForParent(HasParent<?, ?> hasParent) {
        return hasParent.getParent()
                .map(System::identityHashCode)
                .orElse(0);
    }

}
