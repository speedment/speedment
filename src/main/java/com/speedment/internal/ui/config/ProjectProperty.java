package com.speedment.internal.ui.config;

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import static com.speedment.config.db.Project.CONFIG_PATH;
import static com.speedment.config.db.Project.PACKAGE_LOCATION;
import static com.speedment.config.db.Project.PACKAGE_NAME;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.nio.file.Path;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectProperty extends AbstractRootDocumentProperty 
    implements Project, HasEnabledProperty, HasNameProperty {

    public ProjectProperty(Map<String, Object> data) {
        super(data);
    }
    
    public final StringProperty packageNameProperty() {
        return stringPropertyOf(PACKAGE_NAME);
    }

    public final StringProperty packageLocationProperty() {
        return stringPropertyOf(PACKAGE_LOCATION);
    }

    public final ObjectProperty<Path> configPathProperty() {
        Bindings.bindBidirectional(stringPropertyOf(CONFIG_PATH), otherProperty, format);
        return objectPropertyOf(CONFIG_PATH, String.class).;
    }

    @Override
    public Dbms newDbms(Map<String, Object> data) {
        return new DbmsProperty(this, data);
    }
}