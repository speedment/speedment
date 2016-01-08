package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import static com.speedment.config.db.Project.CONFIG_PATH;
import static com.speedment.config.db.Project.PACKAGE_LOCATION;
import static com.speedment.config.db.Project.PACKAGE_NAME;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import org.controlsfx.control.PropertySheet;

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
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties(),
            Stream.of(
                new StringPropertyItem(
                    packageNameProperty(),
                    "Package Name",
                    "The name of the package to place all generated files in. This should be a fully qualified java package name."
                ),
                new StringPropertyItem(
                    packageLocationProperty(),
                    "Package Location",
                    "The folder to store all generated files in. This should be a relative name from the working directory."
                )
            )
        ).flatMap(s -> s);
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
    public BiFunction<Project, Map<String, Object>, DbmsProperty> dbmsConstructor() {
        return DbmsProperty::new;
    }

    @Override
    public Stream<DbmsProperty> dbmses() {
        return (Stream<DbmsProperty>) Project.super.dbmses();
    }
    
    @Override
    protected final Document createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case DBMSES : return new DbmsProperty(this, data);
            default     : return super.createDocument(key, data);
        }
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