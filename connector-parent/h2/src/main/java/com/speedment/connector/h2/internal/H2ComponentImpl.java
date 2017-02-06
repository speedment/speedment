/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 */
package com.speedment.connector.h2.internal;

import static com.speedment.common.injector.State.INITIALIZED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.connector.h2.H2Component;
import com.speedment.connector.h2.internal.H2DbmsType;
import com.speedment.runtime.core.component.DbmsHandlerComponent;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 1.0.0
 */
public final class H2ComponentImpl implements H2Component {

    protected H2ComponentImpl() {}

    @ExecuteBefore(INITIALIZED)
    void onInitialize(
        final @WithState(INITIALIZED) DbmsHandlerComponent dbmsHandlerComponent,
        final @WithState(INITIALIZED) H2DbmsType h2DbmsType
    ) {
        dbmsHandlerComponent.install(h2DbmsType);
    }

}
