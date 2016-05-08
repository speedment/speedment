package com.speedment.runtime.internal.core.platform.component.impl;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.InfoComponent;

/**
 *
 * @author Emil Forslund
 * @since  2.4.0
 */
public final class InfoComponentImpl extends InternalOpenSourceComponent 
    implements InfoComponent {

    public InfoComponentImpl(Speedment speedment) {
        super(speedment);
    }

    @Override
    public String title() {
        return "Speedment";
    }

    @Override
    public String subtitle() {
        return "Open Source";
    }

    @Override
    public String version() {
        return "2.4.0";
    }

    @Override
    public Component defaultCopy(Speedment speedment) {
        return new InfoComponentImpl(speedment);
    }
}