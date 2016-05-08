package com.speedment.runtime.component;

import com.speedment.runtime.annotation.Api;

/**
 *
 * @author Emil Forslund
 * @since  2.4.0
 */
@Api(version = "2.4")
public interface InfoComponent extends Component {
    
    String title();
    String subtitle();
    String version();

    @Override
    public default Class<InfoComponent> getComponentClass() {
        return InfoComponent.class;
    }
}