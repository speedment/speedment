package com.speedment.codegen.model.block;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Initializable {

    Initializable add(final InitializerBlock_ initializer);
    
    public List<InitializerBlock_> getInitializers();
    
}
