package com.speedment.tool.internal.component;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasNullable;
import com.speedment.runtime.config.trait.HasOrderType;
import com.speedment.runtime.config.trait.HasPackageName;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.tool.property.editor.NamePropertyEditor;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import com.speedment.tool.component.PropertyEditorComponent;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.ForeignKeyColumnProperty;
import com.speedment.tool.config.IndexProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.config.trait.HasAliasProperty;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.config.trait.HasNullableProperty;
import com.speedment.tool.config.trait.HasOrderTypeProperty;
import com.speedment.tool.config.trait.HasPackageNameProperty;
import com.speedment.tool.property.editor.AliasPropertyEditor;
import com.speedment.tool.property.editor.AutoIncrementPropertyEditor;
import com.speedment.tool.property.editor.DbmsTypePropertyEditor;
import com.speedment.tool.property.editor.EnabledPropertyEditor;
import com.speedment.tool.property.editor.NullablePropertyEditor;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.editor.CompanyNamePropertyEditor;
import com.speedment.tool.property.editor.ConnectionUrlPropertyEditor;
import com.speedment.tool.property.editor.ForeignKeyColumnEditor;
import com.speedment.tool.property.editor.IpAdressPropertyEditor;
import com.speedment.tool.property.editor.OrderTypePropertyEditor;
import com.speedment.tool.property.editor.PackageLocationPropertyEditor;
import com.speedment.tool.property.editor.PackageNameEditor;
import com.speedment.tool.property.editor.PortNumberEditor;
import com.speedment.tool.property.editor.TypeMapperPropertyEditor;
import com.speedment.tool.property.editor.UniquePropertyEditor;
import com.speedment.tool.property.editor.UsernamePropertyEditor;
import java.util.function.Supplier;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public final class PropertyEditorComponentImpl extends InternalOpenSourceComponent implements PropertyEditorComponent{
    
    private final Map<Class<?>, Map<String, Supplier<PropertyEditor<?>>>> factoryMap; // Ordered based on insert
    private @Inject Injector injector;
    
    public PropertyEditorComponentImpl (){
        this.factoryMap = Collections.synchronizedMap( new LinkedHashMap<>() );
    }
    
    @ExecuteBefore(State.RESOLVED)
    void installEditors() {
        install(HasEnabledProperty.class,   HasEnabled.ENABLED,    EnabledPropertyEditor::new);
        install(HasNameProperty.class,      HasName.NAME,          NamePropertyEditor::new);
        install(HasAliasProperty.class,     HasAlias.ALIAS,        AliasPropertyEditor::new);
        install(HasNullableProperty.class,  HasNullable.NULLABLE,  NullablePropertyEditor::new);
        install(HasOrderTypeProperty.class, HasOrderType.ORDER_TYPE, OrderTypePropertyEditor::new);
        install(ColumnProperty.class,       Column.AUTO_INCREMENT, AutoIncrementPropertyEditor::new);
        install(ColumnProperty.class,       Column.TYPE_MAPPER,    TypeMapperPropertyEditor::new);
        install(DbmsProperty.class,         Dbms.TYPE_NAME,        DbmsTypePropertyEditor::new);
        install(DbmsProperty.class,         Dbms.IP_ADDRESS,       IpAdressPropertyEditor::new);
        install(DbmsProperty.class,         Dbms.PORT,             PortNumberEditor::new);
        install(DbmsProperty.class,         Dbms.USERNAME,         UsernamePropertyEditor::new);
        install(DbmsProperty.class,         Dbms.CONNECTION_URL,   ConnectionUrlPropertyEditor::new);
        install(IndexProperty.class,        Index.UNIQUE,          UniquePropertyEditor::new);
        install(ProjectProperty.class,      Project.COMPANY_NAME,           CompanyNamePropertyEditor::new);
        install(ProjectProperty.class,      Project.PACKAGE_LOCATION,       PackageLocationPropertyEditor::new);
        install(HasPackageNameProperty.class,   HasPackageName.PACKAGE_NAME,          PackageNameEditor::new);
        install(ForeignKeyColumnProperty.class, ForeignKeyColumn.FOREIGN_COLUMN_NAME, ForeignKeyColumnEditor::new);
    }
    
    @Override
    public <DOC extends DocumentProperty> Stream<PropertyEditor.Item> getUiVisibleProperties(DOC document) {

        return MapStream.of(factoryMap)                                         // MapStream<Class, Map<String, Supplier<PropertyEditor>>>
            .filterKey(clazz -> clazz.isAssignableFrom(document.getClass()))    // MapStream<Class, Map<String, Supplier<PropertyEditor>>>
            .flatMapValue(m -> MapStream.of(m))                                 // MapStream<Class, Map.Entry<String, Supplier<PropertyEditor>>>
            .mapKey((clazz, entry) -> entry.getKey())                           // MapStream<String, Map.Entry<String, Supplier<PropertyEditor>>>
            .mapValue(Map.Entry<String, Supplier<PropertyEditor<?>>>::getValue) // MapStream<String, Supplier<PropertyEditor>>
            .distinctKeys((a, b) -> b)                                          // If keys collide, keep newest
            .values()                                                           // Stream<Supplier<PropertyEditor>>
            .map(Supplier::get)                                                 // Stream<PropertyEditor>  (pre inject)
            .map(injector::inject)                                              // Stream<PropertyEditor>  (post inject)
            .map(factory -> {                                                   // Stream<PropertyEditor<DOC>>
                @SuppressWarnings("unchecked")
                final PropertyEditor<DOC> casted =
                    (PropertyEditor<DOC>) factory;
                return casted;
            })
            .flatMap(factory -> factory.fieldsFor(document));                   // Stream<PropertyEditor.Item>
    }

    @Override
    public Class<PropertyEditorComponent> getComponentClass() {
        return PropertyEditorComponent.class;
    }

    @Override
    protected String getDescription() {
        return "Component responsible for building fields for editing properties.";
    }

    @Override
    public <DOC extends DocumentProperty> void install(Class<DOC> documentType, String propertyKey, Supplier<PropertyEditor<DOC>> factory) {
        aquire(documentType).put(propertyKey, factory);
    }
    
    private <DOC extends DocumentProperty> Map<String, Supplier<PropertyEditor<DOC>>> aquire(Class<DOC> documentType){
        @SuppressWarnings("unchecked")
        final Map<String, Supplier<PropertyEditor<DOC>>> result = (Map<String, Supplier<PropertyEditor<DOC>>>) (Object)
            factoryMap.computeIfAbsent(
                documentType, 
                d -> Collections.synchronizedMap( new LinkedHashMap<>() )
            );
        return result;
    }
}
