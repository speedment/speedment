package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.*;
import static com.speedment.config.db.Project.CONFIG_PATH;
import static com.speedment.config.db.Project.PACKAGE_LOCATION;
import static com.speedment.config.db.Project.PACKAGE_NAME;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public final class ProjectMutator extends DocumentMutatorImpl implements DocumentMutator, HasEnabledMutator, HasNameMutator {

    ProjectMutator(Project project) {
        super(project);
    }
    
    public void setPackageName(String packageName) {
        put(PACKAGE_NAME, packageName);
    }

    public void setPackageLocation(String packageLocation) {
        put(PACKAGE_LOCATION, packageLocation);
    }

    public void setConfigPath(String configPath) {
        put(CONFIG_PATH, configPath);
    }
    
    public void add(Dbms dbms) {
        
    }

}