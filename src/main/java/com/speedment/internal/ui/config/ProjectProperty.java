package com.speedment.internal.ui.config;

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectProperty extends AbstractRootDocumentProperty 
    implements Project, HasEnabledProperty, HasNameProperty {

    public ProjectProperty(Map<String, Object> data) {
        super(data);
    }

    @Override
    public Dbms newDbms(Map<String, Object> data) {
        return new DbmsProperty(this, data);
    }
}