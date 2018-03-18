package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.JoinComponent;
import com.speedment.runtime.join.JoinStreamSupplierComponent;

/**
 *
 * @author Per Minborg
 */
public class JoinComponentImpl implements JoinComponent {

    @Inject
    private JoinStreamSupplierComponent streamSupplier;

    @Override
    public <T0> JoinBuilder1<T0> from(TableIdentifier<T0> firstManager) {
        return new JoinBuilder1Impl<>(streamSupplier, firstManager);
    }

}
