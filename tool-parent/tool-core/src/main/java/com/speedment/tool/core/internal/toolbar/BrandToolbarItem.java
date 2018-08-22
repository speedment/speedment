package com.speedment.tool.core.internal.toolbar;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.core.brand.Brand;
import com.speedment.tool.core.toolbar.ToolbarItem;
import com.speedment.tool.core.toolbar.ToolbarSide;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Emil Forslund
 * @since  3.1.5
 */
public final class BrandToolbarItem implements ToolbarItem<ImageView> {

    @Inject
    private Brand brand;

    @Override
    public ImageView createNode() {
        final ImageView view = new ImageView();
        view.setPreserveRatio(true);
        view.setFitHeight(48);
        brand.logoLarge()
            .map(Image::new)
            .ifPresent(view::setImage);
        return view;
    }

    @Override
    public ToolbarSide getSide() {
        return ToolbarSide.RIGHT;
    }
}
