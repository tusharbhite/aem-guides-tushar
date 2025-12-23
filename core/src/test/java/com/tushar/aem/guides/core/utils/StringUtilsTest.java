package com.tushar.aem.guides.core.utils;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void testIsNullOrEmpty() {
        assertTrue(StringUtils.isNullOrEmpty(null), "Null should return true");
        assertTrue(StringUtils.isNullOrEmpty(""), "Empty string should return true");
        assertFalse(StringUtils.isNullOrEmpty(" "), "Space is not empty, should return false");
        assertFalse(StringUtils.isNullOrEmpty("AEM"), "Normal string should return false");
    }

    @Test
    void testConcatenateWithSpace() {
        assertEquals("Hello World", StringUtils.concatenateWithSpace("Hello", "World"));
        assertEquals("World", StringUtils.concatenateWithSpace(null, "World"));
        assertEquals("Hello", StringUtils.concatenateWithSpace("Hello", null));
        assertEquals("World", StringUtils.concatenateWithSpace("", "World"));
        assertEquals("Hello", StringUtils.concatenateWithSpace("Hello", ""));
        assertEquals(null, StringUtils.concatenateWithSpace(null, null));
    }

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException {
        // Use reflection to access the private constructor
        Constructor<StringUtils> constructor = StringUtils.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()),
                "Constructor should be private");

        constructor.setAccessible(true);

        // Verify that instantiating throws the UnsupportedOperationException
        InvocationTargetException exception = assertThrows(InvocationTargetException.class,
                constructor::newInstance);

        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        assertEquals("This is a utility class and cannot be instantiated",
                exception.getCause().getMessage());
    }
}