package com.speedment.tool.internal.brand;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.tool.brand.Brand;
import com.speedment.tool.component.UserInterfaceComponent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
public abstract class AbstractBrand implements Brand {
    
    private @Inject UserInterfaceComponent userInterfaceComponent;
    private @Inject InfoComponent infoComponent;
    
    @Override
    public void apply(Scene scene) {
        final Stage stage = scene.getWindow() == null 
            ? userInterfaceComponent.getStage() 
            : (Stage) scene.getWindow();

        stage.setTitle(infoComponent.title());
        logoSmall()
            .map(Image::new)
            .ifPresent(icon -> {
                stage.getIcons().add(icon);

                @SuppressWarnings("unchecked")
                final Stage dialogStage = (Stage) scene.getWindow();
                if (dialogStage != null) {
                    dialogStage.getIcons().add(icon);
                }
            });

        stylesheets().forEachOrdered(scene.getStylesheets()::add);
    }
}