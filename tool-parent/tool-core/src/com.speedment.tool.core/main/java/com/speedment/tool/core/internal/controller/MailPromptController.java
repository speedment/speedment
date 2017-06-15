/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.runtime.core.internal.util.InternalEmailUtil;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.event.UIEvent;
import com.speedment.tool.core.internal.util.InjectionLoader;
import com.speedment.tool.core.resource.FontAwesome;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 *
 * @author Emil Forslund
 */
public final class MailPromptController implements Initializable {

    private final static Pattern INVALID_MAIL = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    
    private final static Predicate<String> IS_INVALID_MAIL = 
        mail -> !INVALID_MAIL.matcher(mail).find();
    
    private @Inject UserInterfaceComponent userInterfaceComponent;
    private @Inject InjectionLoader injectionLoader;
    private @Inject EventComponent eventComponent;
    
    private @FXML TextField email;
    private @FXML TextArea terms;
    private @FXML Button okey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        okey.setGraphic(FontAwesome.CHECK.view());
        okey.disableProperty().bind(Bindings.createBooleanBinding(
            () -> IS_INVALID_MAIL.test(email.getText()), 
            email.textProperty()
        ));

        okey.setOnAction(ev -> {
            injectionLoader.loadAndShow("Connect");
            InternalEmailUtil.setEmail(email.getText());
            eventComponent.notify(UIEvent.OPEN_CONNECT_WINDOW);
        });
        
        try {
            final InputStream in = MailPromptController.class.getResourceAsStream("/text/terms.txt");
            final String str = inputToString(in);
            terms.setText(str);
        } catch (final IOException ex) {
            userInterfaceComponent.showError(
                "Failed to load file", 
                "The terms and conditions of this software couldn't be loaded.", 
                ex
            );
        }
        
    }
    
    private static String inputToString(InputStream in) throws IOException {
        try (final BufferedInputStream bis = new BufferedInputStream(in)) {
            try (final ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
                int result = bis.read();
                while(result != -1) {
                    byte b = (byte) result;
                    buf.write(b);
                    result = bis.read();
                }
                return buf.toString();
            }
        }
    }
}