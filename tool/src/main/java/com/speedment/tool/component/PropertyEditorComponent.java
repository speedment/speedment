package com.speedment.tool.component;

import com.speedment.tool.property.PropertyEditor;
import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.Component;
import com.speedment.tool.config.DocumentProperty;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A component for drawing certain editable UI properties.
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
@InjectorKey(PropertyEditorComponent.class)
public interface PropertyEditorComponent extends Component {
    
    /**
     * Retrieves a Stream of UI editor items, that's been installed under 
     * a certain document type.
     * 
     * @param <DOC>    the document type
     * @param document the document
     * @return         all editor items for the document
     */
    <DOC extends DocumentProperty> Stream<PropertyEditor.Item> getUiVisibleProperties(DOC document);
    
    /**
     * Installs a new supplier for an editor item under a certain document type and key. 
     * <P>
     * Calls to {@see getUiVisibleProperties} will retrieve items depending on 
     * what editors have been installed under the document's type. Installations also
     * has to provide a unique key for the particular editor. If two editors are installed
     * on the same document type, with the same key, only the last installed editor
     * will be produced.
     * 
     * @param <DOC>         the document type
     * @param documentType  the document type
     * @param propertyKey   the property key
     * @param factory       supplier for creating the editor
     */
    <DOC extends DocumentProperty> void install(Class<DOC> documentType, String propertyKey, Supplier<PropertyEditor<DOC>> factory);    
   
    @Override
    default Class<PropertyEditorComponent> getComponentClass(){
        return PropertyEditorComponent.class;
    }
    
}
