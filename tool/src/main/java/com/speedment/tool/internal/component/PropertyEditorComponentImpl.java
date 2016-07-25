package com.speedment.tool.internal.component;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.tool.property.NamePropertyEditor;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;
import com.speedment.tool.component.PropertyEditorComponent;
import com.speedment.tool.config.trait.HasAliasProperty;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.property.AliasPropertyEditor;
import com.speedment.tool.property.EnabledPropertyEditor;
import com.speedment.tool.property.PropertyEditor;

/**
 *
 * @author Simon
 */
public final class PropertyEditorComponentImpl extends InternalOpenSourceComponent implements PropertyEditorComponent{
    
    private final Map<Class<?>, Map<String, PropertyEditor<?>>> factoryMap; // Ordered based on insert
    
    public PropertyEditorComponentImpl (){
        this.factoryMap = Collections.synchronizedMap( new LinkedHashMap<>() );
    }
    
    @ExecuteBefore(State.RESOLVED)
    void installEditors() {
        install(HasEnabledProperty.class, new EnabledPropertyEditor());
        install(HasNameProperty.class, new NamePropertyEditor());
        install(HasAliasProperty.class, new AliasPropertyEditor());
    }
    
    @Override
    public <DOC extends DocumentProperty> Stream<PropertySheet.Item> getUiVisibleProperties(DOC document) {

        return MapStream.of(factoryMap)                                         // MapStream<Class, Map<String, DocumentEditorFactory>>
            .filterKey(clazz -> clazz.isAssignableFrom(document.getClass()))    // MapStream<Class, Map<String, DocumentEditorFactory>>
            .flatMapValue(m -> MapStream.of(m))                                 // MapStream<Class, Map.Entry<String, DocumentEditorFactory>>
            .mapKey((clazz, entry) -> entry.getKey())                           // MapStream<String, Map.Entry<String, DocumentEditorFactory>>
            .mapValue(Map.Entry<String, PropertyEditor<?>>::getValue)           // MapStream<String, DocumentEditorFactory>
            .distinctKeys((a, b) -> b)                                          // If keys collide, keep newest
            .values()                                                           // Stream<DocumentEditorFactory>
            .map(factory -> {                                                   // Stream<DocumentEditorFactory<DOC>>
                @SuppressWarnings("unchecked")
                final PropertyEditor<DOC> casted =
                    (PropertyEditor<DOC>) factory;
                return casted;
            })
            .flatMap(factory -> factory.fieldsFor(document));                // Stream<PropertySheet.Item>
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
    public <DOC extends DocumentProperty> void install(Class<DOC> documentType, PropertyEditor<DOC> factory) {
        aquire(documentType).put(factory.getPropertyKey(), factory);
    }
    
    private <DOC extends DocumentProperty> Map<String, PropertyEditor<DOC>> aquire(Class<DOC> documentType){
        @SuppressWarnings("unchecked")
        final Map<String, PropertyEditor<DOC>> result = (Map<String, PropertyEditor<DOC>>) (Object)
            factoryMap.computeIfAbsent(
                documentType, 
                d -> Collections.synchronizedMap( new LinkedHashMap<>() )
            );
        return result;
    }
}
