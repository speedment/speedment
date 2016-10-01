/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.config.identifier;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class TableIdentifierTest {

    @Test
    public void testOf() {
        final String db = "db";
        final String sc = "sc";
        final String ta = "ta";

        TableIdentifier<Integer> ti0 = TableIdentifier.of(db, sc, ta);
        TableIdentifier<Integer> ti1 = TableIdentifier.of(db, sc, ta);
        TableIdentifier<Integer> ti2 = TableIdentifier.of(db, sc, "Arne");

        assertTrue(ti0 == ti1); // Make sure that the interface interns duplicates
        assertFalse(ti0 == ti2);

    }

}
