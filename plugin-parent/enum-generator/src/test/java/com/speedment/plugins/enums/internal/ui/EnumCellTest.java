package com.speedment.plugins.enums.internal.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

final class EnumCellTest {

    private static final String TEXT = "Saturn";

    private List<String> list;
    private ObservableList<String> observableList;
    private EnumCell instance;
    private StringConverter<String> converter;

    @BeforeEach
    void setup() {
        if (!INITIALIZED.get()) {
            new Thread(() -> Application.launch(JavaFXInitializer.class)).start();
        }
        while(!INITIALIZED.get()) {
            // Spin wait
        }
        list = Arrays.asList("A", "B", "C");
        observableList = FXCollections.observableList(list);
        instance = new EnumCell(observableList);
        converter = instance.getConverter();
    }

    @Test
    void testToString() {
        final String actual = converter.toString(TEXT);
        assertEquals(TEXT, actual);
    }

    @Test
    void testToStringNull() {
        assertThrows(NullPointerException.class, () -> converter.fromString(null));
    }

    @Test
    void testFromStringDup() {
        assertNull(converter.fromString("A"));
    }

    @Test
    void testFromStringIllegal() {
        assertNull(converter.fromString("7%^45$"));
    }

    @Test
    void testFromStringSpace() {
        assertEquals("A B", converter.fromString("A B"));
    }

    @Test
    void testFromString() {
        final String actual = converter.fromString("");
        assertEquals(null, actual); // label is null before startEdit()
    }

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean();

    public static final class JavaFXInitializer extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            // JavaFX should be initialized
            INITIALIZED.set(true);
        }
    }
}