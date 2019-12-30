package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class HasLicenseTermTest {

    private static final String TEXT = "A";

    private HasLicenseTerm<File> file;

    @BeforeEach
    void setup() {
        file = File.of("a");
    }

    @Test
    void set() {
        file.set(LicenseTerm.of(TEXT));
        assertEquals(TEXT, file.getLicenseTerm().orElseThrow(NoSuchElementException::new).getText());
    }

    @Test
    void licenseTerm() {
        file.licenseTerm(TEXT);
        assertEquals(TEXT, file.getLicenseTerm().orElseThrow(NoSuchElementException::new).getText());
    }

    @Test
    void getLicenseTerm() {
        assertEquals(Optional.empty(), file.getLicenseTerm());
    }
}