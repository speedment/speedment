package com.speedment.internal.ui.config;

import com.speedment.config.db.Column;
import static com.speedment.config.db.Column.AUTO_INCREMENT;
import static com.speedment.config.db.Column.DATABASE_TYPE;
import static com.speedment.config.db.Column.NULLABLE;
import static com.speedment.config.db.Column.TYPE_MAPPER;
import com.speedment.config.db.Table;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import com.speedment.internal.ui.property.StringPropertyItem;
import com.speedment.internal.ui.property.TypeMapperPropertyItem;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ColumnProperty extends AbstractChildDocumentProperty<Table> 
    implements Column, HasEnabledProperty, HasNameProperty, HasAliasProperty {
    
    public ColumnProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties(),
            HasAliasProperty.super.getUiVisibleProperties(),
            Stream.of(
                new TypeMapperPropertyItem(
                    null,
                    Optional.ofNullable(findTypeMapper())
                        .map(tm -> (Class) tm.getDatabaseType())
                        .orElse(findDatabaseType()),
                    typeMapperObjectProperty(),
                    "JDBC Type to Java",
                    "The class that will be used to map types between the database and the generated code."
                ),
                new BooleanPropertyItem(
                    nullableProperty(),
                    "Is Nullable",
                    "If this column can hold 'null'-values or not."
                ),
                new BooleanPropertyItem(
                    autoIncrementProperty(),
                    "Is Auto Incrementing",
                    "If this column will increment automatically for each new entity."
                )
            )
        ).flatMap(s -> s);
    }

    public BooleanProperty nullableProperty() {
        return booleanPropertyOf(NULLABLE);
    }

    public BooleanProperty autoIncrementProperty() {
        return booleanPropertyOf(AUTO_INCREMENT);
    }
 
    public StringProperty typeMapperProperty() {
        return stringPropertyOf(TYPE_MAPPER);
    }
  
    public Property<TypeMapper<?, ?>> typeMapperObjectProperty() {
        final Property<TypeMapper<?, ?>> pathProperty = new SimpleObjectProperty<>();
        
        Bindings.bindBidirectional(
            typeMapperProperty(), 
            pathProperty, 
            TYPE_MAPPER_CONVERTER
        );
        
        return pathProperty;
    }

    public StringProperty databaseTypeProperty() {
        return stringPropertyOf(DATABASE_TYPE);
    }

    public ObjectBinding<Class<?>> databaseTypeObjectProperty() {
        return createObjectBinding(this::findDatabaseType, databaseTypeProperty());
    }
    
    private final static StringConverter<TypeMapper<?, ?>> TYPE_MAPPER_CONVERTER = new StringConverter<TypeMapper<?, ?>>() {
        @Override
        public String toString(TypeMapper<?, ?> typeMapper) {
            return typeMapper.getClass().getName();
        }

        @Override
        public TypeMapper<?, ?> fromString(String className) {
            try {
                @SuppressWarnings("unchecked")
                final TypeMapper<?, ?> typeMapper = (TypeMapper<?, ?>) Class.forName(className).newInstance();
                return typeMapper;
            } catch (final ClassNotFoundException ex) {
                throw new SpeedmentException("Could not find type-mapper class: '" + className + "'.", ex);
            } catch (InstantiationException ex) {
                throw new SpeedmentException("Could not instantiate type-mapper class: '" + className + "'.", ex);
            } catch (IllegalAccessException ex) {
                throw new SpeedmentException("Could not access type-mapper class: '" + className + "'.", ex);
            }
        }
    };
}