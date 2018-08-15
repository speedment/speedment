package com.speedment.tool.core.util;

import com.speedment.common.injector.annotation.InjectKey;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.function.Supplier;

/**
 * @author Emil Forslund
 * @since  3.1.5
 */
@InjectKey(InjectionLoader.class)
public interface InjectionLoader {

    Node load(String name);

    Parent loadAndShow(String name);

    Parent loadAsModal(String name);

    <T extends Initializable> void install(Class<T> controllerClass, Supplier<T> newController);

}
