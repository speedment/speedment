package com.speedment.tool.component;

import com.speedment.tool.property.PropertyEditor;
import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.component.Component;
import com.speedment.tool.config.DocumentProperty;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 * A component for drawing certain editable UI properties.
 *
 * @author Simon
 */

@InjectorKey(PropertyEditorComponent.class)
public interface PropertyEditorComponent extends Component {
    
    <DOC extends DocumentProperty> Stream<PropertySheet.Item> getUiVisibleProperties(DOC document);
    
    <DOC extends DocumentProperty> void install(Class<DOC> documentType, PropertyEditor<DOC> factory);    
   
    @Override
    default Class<PropertyEditorComponent> getComponentClass(){
        return PropertyEditorComponent.class;
    }
    
}
