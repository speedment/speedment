package com.speedment.runtime.config.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.internal.util.document.TraitUtil.AbstractTraitView;
import static com.speedment.runtime.internal.util.document.TraitUtil.viewOf;
import com.speedment.runtime.util.OptionalBoolean;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Simon
 */
@Api(version="3.0")
public interface HasPackageName extends Document{
    
    final String PACKAGE_NAME = "packageName";  
    final String USE_DEFAULT_PACKAGE_NAME = "useDefaultPackageName";
    
    /**
     * Returns the name of the generated package where this document will be
     * located.
     *
     * @return the name of the generated package or {@code empty}
     */
    default Optional<String> getPackageName() {
        return getAsString(PACKAGE_NAME);
    }
    
    /**
     * Return whether this document uses a custom package name or the 
     * default package name.
     * 
     * @return  whether a custom package name is used
     */
    default OptionalBoolean useDefaultPackageName() {
        return getAsBoolean(USE_DEFAULT_PACKAGE_NAME);
    }
    
        /**
     * Returns a wrapper of the specified document that implements the 
     * {@link HasPackageName} trait. If the specified document already implements the
     * trait, it is returned unwrapped.
     * 
     * @param document  the document to wrap
     * @return          the wrapper
     */
    static HasPackageName of(Document document) {
        return viewOf(document, HasPackageName.class, HasPackageNameView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasEnabled} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
class HasPackageNameView extends AbstractTraitView implements HasPackageName {

    /**
     * Constructs a new enabled view of with the specified parent and data.
     * 
     * @param parent         the parent of the wrapped document
     * @param data           the data of the wrapped document
     * @param mainInterface  the main interface of the wrapped document
     */
    HasPackageNameView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}
