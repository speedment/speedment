package com.speedment.internal.ui.config;

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import static com.speedment.config.db.Project.CONFIG_PATH;
import static com.speedment.config.db.Project.PACKAGE_LOCATION;
import static com.speedment.config.db.Project.PACKAGE_NAME;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

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
        final ObjectProperty<Path> pathProperty = new SimpleObjectProperty<>();
        
        Bindings.bindBidirectional(
            stringPropertyOf(CONFIG_PATH), 
            pathProperty, 
            PATH_CONVERTER
        );
        
        return pathProperty;
    }

    @Override
    public BiFunction<Project, Map<String, Object>, Dbms> dbmsConstructor() {
        return DbmsProperty::new;
    }
    
    private final static StringConverter<Path> PATH_CONVERTER = new StringConverter<Path>() {
        @Override
        public String toString(Path p) {
            return p.toString();
        }

        @Override
        public Path fromString(String string) {
            return Paths.get(string);
        }
    };
}