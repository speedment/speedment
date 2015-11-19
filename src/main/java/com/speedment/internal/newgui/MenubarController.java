package com.speedment.internal.newgui;

import com.speedment.Speedment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author Emil Forslund
 */
public final class MenubarController implements Initializable {
    
    private final Speedment speedment;
    
    public MenubarController(Speedment speedment) {
        this.speedment = speedment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Creates and configures a new Notification-component in the specified stage.
     *
     * @param parent   the parent to show it in
     * @param message  the message to display
     * @param type     the type of notification
     */
    public static void showNotification(Pane parent, String message, Notification type) {
        requireNonNulls(parent, message, type);
		final FXMLLoader loader = new FXMLLoader(AlertController.class.getResource("/fxml/Notification.fxml"));
		final NotificationController control = new NotificationController(message, type);
        loader.setController(control);

        try {
            final VBox notification = (VBox) loader.load();
            parent.getChildren().add(notification);
            enterFromRight(notification);
            
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
	}
}