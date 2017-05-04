package com.speedment.tool.core.resource;

import com.speedment.tool.core.exception.SpeedmentToolException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Emil Forslund
 * @since  3.0.8
 */
public class FontAwesomeIconTest {

    @Test
    void testAllIconsExist() {
        for (final FontAwesomeIcon icon : FontAwesomeIcon.values()) {
            try (final InputStream in = icon.getFileInputStream()) {
                assertNotNull(in);
            } catch (final SpeedmentToolException ex) {
                fail(ex.getMessage());
            } catch (final IOException ex) {
                throw new RuntimeException(
                    "Error closing input stream in test.", ex
                );
            }
        }
    }
}