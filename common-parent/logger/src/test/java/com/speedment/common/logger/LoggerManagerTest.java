package com.speedment.common.logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.logging.LogManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

final class LoggerManagerTest {

    private static LoggerFactory defaultLoggerFactory;

    @Mock
    private LoggerFactory loggerFactory;
    private LogManager instance;

    @BeforeAll
    static void save() {
        defaultLoggerFactory = LoggerManager.getFactory();
    }

    @AfterEach
    void restore() {
        LoggerManager.setFactory(defaultLoggerFactory);
    }

    @Test
    void setFactory() {
        LoggerManager.setFactory(loggerFactory);
        assertSame(loggerFactory, LoggerManager.getFactory());
    }

    @Test
    void getFactory() {
        assertSame(defaultLoggerFactory, LoggerManager.getFactory());
    }

    @Test
    void getLogger() {
        assertSame(defaultLoggerFactory.create(LoggerManagerTest.class), LoggerManager.getLogger(LoggerManagerTest.class));
    }

    @Test
    void testGetLogger() {
        assertSame(defaultLoggerFactory.create(LoggerManagerTest.class.getName() + "A"), LoggerManager.getLogger(LoggerManagerTest.class.getName() + "A"));
    }

    @Test
    void setLevel() {
        final Logger logger = LoggerManager.getLogger(LoggerManagerTest.class);
        LoggerManager.setLevel(LoggerManagerTest.class, Level.FATAL);
        assertEquals(Level.FATAL, logger.getLevel());
    }

    @Test
    void testSetLevel() {
        final Logger logger = LoggerManager.getLogger(LoggerManagerTest.class.getName() + "A");
        LoggerManager.setLevel(LoggerManagerTest.class.getName() + "A", Level.FATAL);
        assertEquals(Level.FATAL, logger.getLevel());

    }
}