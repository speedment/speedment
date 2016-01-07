package com.speedment.internal.ui.config;

import com.speedment.config.db.Dbms;
import static com.speedment.config.db.Dbms.IP_ADDRESS;
import static com.speedment.config.db.Dbms.PORT;
import static com.speedment.config.db.Dbms.USERNAME;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;
import java.util.function.BiFunction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsProperty extends AbstractChildDocumentProperty<Project> 
    implements Dbms, HasEnabledProperty, HasNameProperty {

    public DbmsProperty(Project parent, Map<String, Object> data) {
        super(parent, data);
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
    public BiFunction<Dbms, Map<String, Object>, Schema> schemaConstructor() {
        return SchemaProperty::new;
    }
}