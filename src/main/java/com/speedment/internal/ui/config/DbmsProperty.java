package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import static com.speedment.config.db.Dbms.IP_ADDRESS;
import static com.speedment.config.db.Dbms.PORT;
import static com.speedment.config.db.Dbms.USERNAME;
import com.speedment.config.db.Project;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.IntegerPropertyItem;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsProperty extends AbstractChildDocumentProperty<Project> 
    implements Dbms, HasEnabledProperty, HasNameProperty {

    public DbmsProperty(Project parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties(),
            Stream.of(
                // TODO: Add DbmsType
                new StringPropertyItem(
                    ipAddressProperty(),       
                    "IP Address",                  
                    "The ip of the database host."
                ),
                new IntegerPropertyItem(
                    portProperty(),       
                    "Port",                  
                    "The port of the database on the database host."
                ),
                new StringPropertyItem(
                    usernameProperty(),      
                    "Username",                  
                    "The username to use when connecting to the database."
                )
            )
        ).flatMap(s -> s);
    }
    
    public final StringProperty typeNameProperty() {
        return stringPropertyOf(TYPE_NAME);
    }

    public final StringProperty ipAddressProperty() {
        return stringPropertyOf(IP_ADDRESS);
    }

    public final IntegerProperty portProperty() {
        return integerPropertyOf(PORT);
    }

    public final StringProperty usernameProperty() {
        return stringPropertyOf(USERNAME);
    }

    @Override
    public BiFunction<Dbms, Map<String, Object>, SchemaProperty> schemaConstructor() {
        return SchemaProperty::new;
    }

    @Override
    public Stream<SchemaProperty> schemas() {
        return (Stream<SchemaProperty>) Dbms.super.schemas();
    }
    
    @Override
    protected final Document createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case SCHEMAS : return new SchemaProperty(this, data);
            default      : return super.createDocument(key, data);
        }
    }
}