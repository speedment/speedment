package com.speedment.config.aspects;

import com.speedment.annotation.Api;
import com.speedment.annotation.External;

/**
 *
 * @author pemi
 */
@Api(version = "2.3")
public interface RestExposable {
    
    @External(type = Boolean.class, isVisibleInGui = false)
    boolean isExposedInRest();
    
    @External(type = Boolean.class, isVisibleInGui = false)
    void setExposedInRest(boolean exposed);
    
    @External(type = String.class, isVisibleInGui = false)
    String getRestPath();
    
    @External(type = String.class, isVisibleInGui = false)
    void setRestPath(String path);
}