package com.speedment.internal.newgui;

import com.speedment.Speedment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author Emil Forslund
 */
public final class SceneController implements Initializable {
    
    private final Speedment speedment;
    private @FXML 
    
    public SceneController(Speedment speedment) {
        this.speedment = speedment;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}