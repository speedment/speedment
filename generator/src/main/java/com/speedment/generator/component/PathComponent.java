package com.speedment.generator.component;

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.component.Component;
import java.nio.file.Path;

/**
 * A component that can be used to determine the {@code Path} to the project.
 * This is used by the generator to determine where to put generated files.
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
@InjectorKey(PathComponent.class)
public interface PathComponent extends Component {

    @Override
    default Class<? extends Component> getComponentClass() {
        return PathComponent.class;
    }
    
    /**
     * Returns the base directory of the project (the same folder as the 
     * {@code pom.xml}-file is located in for Maven-projects.
     * 
     * @return  the base directory
     */
    Path baseDir();
    
    /**
     * Returns the root folder where generated sources will be put. This is
     * usually the same folder as the {@code com}-folder is located in if the
     * generated package name starts with {@code com.company}.
     * 
     * @return  the generated package location
     */
    Path packageLocation();
    
}