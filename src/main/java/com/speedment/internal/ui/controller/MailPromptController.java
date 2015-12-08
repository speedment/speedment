/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui.controller;

import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.util.Settings;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Emil Forslund
 */
public final class MailPromptController implements Initializable {
    
    public final static String MAIL_FIELD = "user_mail";
    
    private final static Pattern INVALID_MAIL = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    
    private final static Predicate<String> IS_INVALID_MAIL = 
        mail -> !INVALID_MAIL.matcher(mail).find();
    
    private final UISession session;
    private @FXML TextField email;
    private @FXML Button okey;
    
    private MailPromptController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        okey.disableProperty().bind(Bindings.createBooleanBinding(
            () -> IS_INVALID_MAIL.test(email.getText()), 
            email.textProperty()
        ));

        okey.setOnAction(ev -> {
            ConnectController.createAndShow(session);

            try {
                Settings.inst().set(MAIL_FIELD, email.getText());
            } catch (RuntimeException ex) {
                session.showError("Error!", "Could not save settings file", ex);
            }
        });
    }
    
    public static void createAndShow(UISession session) {
        Loader.createAndShow(session, "MailPrompt", MailPromptController::new);
	}
}