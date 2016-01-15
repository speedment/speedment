/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui.util;

import com.speedment.internal.ui.UISession;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.resource.SpeedmentIcon;
import java.io.IOException;
import java.util.function.Function;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.function.Consumer;
import static com.speedment.util.NullUtil.requireNonNulls;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static com.speedment.util.NullUtil.requireNonNulls;

/**
 *
 * @author Emil Forslund
 */
public final class Loader {
    
    public static final String 
        FILENAME_PREFIX = "/fxml/",
        FILENAME_SUFFIX = ".fxml";
    
    public static <T extends Initializable> Parent create(UISession session, String filename, Function<UISession, T> constructor) {
        return create(session, filename, constructor, e -> {});
	}
    
    public static <T extends Initializable> Parent create(UISession session, String filename, Function<UISession, T> constructor, Consumer<T> consumer) {
        requireNonNulls(session, filename, constructor, consumer);
		final FXMLLoader loader = new FXMLLoader(Loader.class.getResource(FILENAME_PREFIX + filename + FILENAME_SUFFIX));
		final T control = constructor.apply(session);
        loader.setController(control);

        try {
            final Parent loaded = loader.load();
            consumer.accept(control);
            return loaded;
        } catch (IOException ex) {
            throw new SpeedmentException(ex);
        }
	}
    
    public static <T extends Initializable> Parent createAndShow(UISession session, String filename, Function<UISession, T> constructor) {
        return createAndShow(session, filename, constructor, e -> {});
    }
    
    public static <T extends Initializable> Parent createAndShow(UISession session, String filename, Function<UISession, T> constructor, Consumer<T> consumer) {
        final Parent root = Loader.create(session, filename, constructor, consumer);
        final Scene scene = new Scene(root);
        
        scene.getStylesheets().add(session.getSpeedment().getUserInterfaceComponent().getStylesheetFile());
        
        final Stage stage = session.getStage();
        stage.hide();
        stage.getIcons().add(SpeedmentIcon.SPIRE.load());
        stage.setTitle("Speedment");
        stage.setScene(scene);
        stage.show();
        
        return root;
    }
    
    private Loader() {
        instanceNotAllowed(getClass());
    }
}
