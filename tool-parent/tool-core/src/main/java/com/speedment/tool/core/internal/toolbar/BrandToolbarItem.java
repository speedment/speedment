/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.tool.core.internal.toolbar;

import com.speedment.tool.core.brand.Brand;
import com.speedment.tool.core.toolbar.ToolbarItem;
import com.speedment.tool.core.toolbar.ToolbarSide;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.5
 */
public final class BrandToolbarItem implements ToolbarItem<ImageView> {

    private final Brand brand;

    public BrandToolbarItem(Brand brand) {
        this.brand = requireNonNull(brand);
    }

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
