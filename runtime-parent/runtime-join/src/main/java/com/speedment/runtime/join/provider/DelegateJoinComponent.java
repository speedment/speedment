package com.speedment.runtime.join.provider;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.JoinComponent;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.builder.JoinBuilder1;
import com.speedment.runtime.join.internal.component.join.JoinComponentImpl;

public final class DelegateJoinComponent implements JoinComponent  {

    private final JoinComponent inner;

    public DelegateJoinComponent(JoinStreamSupplierComponent streamSupplier) {
        this.inner = new JoinComponentImpl(streamSupplier);
    }

    @Override
    public <T0> JoinBuilder1<T0> from(TableIdentifier<T0> firstTableIdentifier) {
        return inner.from(firstTableIdentifier);
    }
}
