package com.speedment.runtime.internal.runtime;

import com.speedment.runtime.ApplicationMetadata;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.internal.config.ProjectImpl;
import com.speedment.runtime.internal.util.document.DocumentTranscoder;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
public abstract class AbstractApplicationMetadata implements ApplicationMetadata {

    protected AbstractApplicationMetadata() {}
    
    /**
     * Returns the meta data as a String that shall be used to build up the
     * complete Project meta data. If no metadata exists, returns an
     * empty optional.
     *
     * @return the meta data or empty if none exists for this session
     */
    protected abstract Optional<String> getMetadata();
    
    @Override
    public Project makeProject() {
        return getMetadata().map(DocumentTranscoder::load).orElseGet(() -> {
            final Map<String, Object> data = new ConcurrentHashMap<>();
            data.put(HasName.NAME, "Project");
            return new ProjectImpl(data);
        });
    }
}