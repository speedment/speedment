package com.speedment.runtime.component;

import com.speedment.runtime.annotation.Api;

/**
 * Contains general information about the installment like the software title
 * and version. This is used to print correct messages for an example when the
 * application launches.
 * 
 * @author Emil Forslund
 * @since  2.4.0
 */
@Api(version = "2.4")
public interface InfoComponent extends Component {
    
    /**
     * The title of Speedment.
     * 
     * @return  the title
     */
    String title();
    
    /**
     * The subtitle of Speedment.
     * 
     * @return  the subtitle
     */
    String subtitle();
    
    /**
     * The version of Speedment.
     * 
     * @return  the version
     */
    String version();

    /**
     * {@inheritDoc} 
     */
    @Override
    public default Class<InfoComponent> getComponentClass() {
        return InfoComponent.class;
    }
}