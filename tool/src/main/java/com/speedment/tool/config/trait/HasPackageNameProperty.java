package com.speedment.tool.config.trait;

import com.speedment.common.injector.Injector;
import com.speedment.generator.TranslatorSupport;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.trait.HasPackageName;
import com.speedment.runtime.util.OptionalBoolean;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.ProjectProperty;
import java.util.Optional;
import static javafx.beans.binding.Bindings.createStringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version = "3.0")
public interface HasPackageNameProperty extends DocumentProperty, HasPackageName, HasNameProperty {
    
    default StringProperty packageNameProperty(){
        return stringPropertyOf(HasPackageName.PACKAGE_NAME, () -> null);
    }
    
    @Override
    default Optional<String> getPackageName() {
        if( useDefaultPackageName().getAsBoolean() ){
            System.out.println("default");
        }
        return Optional.ofNullable(packageNameProperty().get());
    }
    
    default BooleanProperty useDefaultPackageNameProperty(){
        return booleanPropertyOf(HasPackageName.USE_DEFAULT_PACKAGE_NAME, () -> true);
    }
    
    @Override
    default OptionalBoolean useDefaultPackageName(){
        return OptionalBoolean.of(useDefaultPackageNameProperty().get());
    }
    
    /**
     * Returns the default value for the package name
     *
     * @param injector  the injector, in case an implementing class needs it
     * @return          the default package name 
     */
    default ObservableStringValue defaultPackageNameProperty(Injector injector) {
        final TranslatorSupport<?> support = new TranslatorSupport<>(injector, this);
        
        if (this instanceof ProjectProperty) {
            final ProjectProperty project = (ProjectProperty) this;
            return createStringBinding(
                support::defaultPackageName, 
                project.nameProperty(), 
                project.companyNameProperty()
            );
        } else if (this instanceof HasAliasProperty) {
            final HasAliasProperty alias = (HasAliasProperty) this;
            return createStringBinding(support::defaultPackageName, alias.aliasProperty());
        } else {
            return createStringBinding(support::defaultPackageName, nameProperty());
        }
    }
}
