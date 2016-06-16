package com.speedment.runtime.internal.runtime;

import com.speedment.common.injector.annotation.Config;
import com.speedment.runtime.ApplicationMetadata;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.internal.util.document.DocumentTranscoder;
import java.io.File;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
public final class DefaultApplicationMetadata implements ApplicationMetadata {
    
    public final static String METADATA_LOCATION = "metadata_location";
    
    private @Config(
        name=METADATA_LOCATION, 
        value="src/main/json/speedment.json"
    ) File metadataLocation;

    @Override
    public Project makeProject() {
        return DocumentTranscoder.load(metadataLocation.toPath());
    }
}