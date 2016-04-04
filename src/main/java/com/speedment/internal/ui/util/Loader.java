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

import com.speedment.component.UserInterfaceComponent.Brand;
import com.speedment.internal.ui.UISession;
import com.speedment.exception.SpeedmentException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static com.speedment.util.NullUtil.requireNonNulls;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Emil Forslund
 */
public final class Loader {
    
    public static final String 
        FILENAME_PREFIX = "/fxml/",
        FILENAME_SUFFIX = ".fxml";
    
    public static Parent create(UISession session, String filename) {
        return create(session, filename, e -> {});
	}
    
    public static Parent create(UISession session, String filename, Consumer<Initializable> consumer) {
        requireNonNulls(session, filename, consumer);
		final FXMLLoader loader = new FXMLLoader(Loader.class.getResource(FILENAME_PREFIX + filename + FILENAME_SUFFIX));
        final AtomicReference<Initializable> constructed = new AtomicReference<>();
        loader.setControllerFactory(clazz -> {
            try {
                @SuppressWarnings("unchecked")
                final Constructor<? extends Initializable> constructor = 
                    (Constructor<? extends Initializable>) 
                    clazz.getDeclaredConstructor(UISession.class);
                
                constructor.setAccessible(true);
                
                final Initializable obj = constructor.newInstance(session);
                constructed.set(obj);
                return obj;
            } catch (final IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                throw new SpeedmentException(
                    "Could not construct a controller '" + clazz.getName() + 
                    "' for file '" + filename + "' using reflection.", ex
                );
            }
        });

        try {
            final Parent loaded = loader.load();
            consumer.accept(constructed.get());
            return loaded;
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Failed to load FXML file '" + filename + "'.", ex
            );
        } catch (final NullPointerException ex) {
            throw new SpeedmentException(
                "Produced instance for FXML-file: '" + filename + 
                "' was set to null.", ex
            );
        }
	}
    
    public static Parent createAndShow(
        UISession session, String filename) {
        
        return createAndShow(session, filename, e -> {});
    }
    
    public static Parent createAndShow(
        UISession session, String filename, Consumer<Initializable> consumer) {
        
        final Parent root = Loader.create(session, filename, consumer);
        final Scene scene = new Scene(root);
        
        final Stage stage = session.getStage();
        stage.hide();
        Brand.apply(session, scene);
        stage.setScene(scene);
        stage.show();
        
        return root;
    }
    
    
    /**
     * Utility classes should not be instantiated.
     */
    private Loader() {
        instanceNotAllowed(getClass());
    }
}
