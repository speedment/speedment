package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.requireNonNull;

final class EnumCell extends TextFieldListCell<String> {

    private static final Logger LOGGER = LoggerManager.getLogger(EnumCell.class);

    private final ObservableList<String> strings;
    private String labelString;

    EnumCell(ObservableList<String> strings) {
        super();
        this.strings = requireNonNull(strings);
        setConverter(myConverter());
    }

    @Override
    public void startEdit() {
        labelString = getText();
        super.startEdit();
    }

    private StringConverter<String> myConverter() {
        return new MyStringConverter();
    }

    protected final class MyStringConverter extends StringConverter<String> {

        @Override
        public String toString(String value) {
            return (value != null) ? value : "";
        }

        @Override
        public String fromString(String value) {
            // Avoid false positives (ie showing an error that we match ourselves)
            if (value.equalsIgnoreCase(labelString)) {
                return value;
            } else if (value.isEmpty()) {
                LOGGER.info("An enum field cannot be empty. Please remove the field instead.");
                return labelString;
            }

            // Make sure this is not a duplicate entry
            final AtomicBoolean duplicate = new AtomicBoolean(false);
            strings.stream()
                .filter(elem -> elem.equalsIgnoreCase(value))
                .forEach(elem -> duplicate.set(true));
            if (duplicate.get()){
                LOGGER.info("Enum cannot contain the same constant twice");
                return labelString;

            // Make sure this entry contains only legal characters
            } else if ( !value.matches("([\\w\\-\\_\\ ]+)")) {
                LOGGER.info("Enum should only contain letters, number, underscore and/or dashes");
                return labelString;

            // Warn if it contains a space
            } else if (value.contains(" ")) {
                LOGGER.warn("Enum spaces will be converted to underscores in Java");
                return value;
            } else {
                return value;
            }
        }
    }
}
