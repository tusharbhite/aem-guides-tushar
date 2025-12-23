package com.tushar.aem.guides.core.configurations;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class MyConfigServiceImplTest {

    private final AemContext context = new AemContext();
    private MyConfigServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new MyConfigServiceImpl();
    }

    @Test
    void testActivateAndGetters() {
        // 1. Prepare a map of properties mimicking the OSGi configuration values
        // Note: The keys must match the method names in MyConfigServiceInterface
        Map<String, Object> configProps = new HashMap<>();
        configProps.put("getStudentName", "Tushar");
        configProps.put("getRollNumber", 101);
        configProps.put("getRegular", true);
        configProps.put("getSubjects", new String[]{"Math", "Science"});
        configProps.put("getCountries", "India");

        // 2. Register, Inject, and Activate the service
        context.registerInjectActivateService(studentService, configProps);

        // 3. Verify that the start() method correctly mapped the values
        assertEquals("Tushar", studentService.getStudentName());
        assertEquals(101, studentService.getRollNumber());
        assertTrue(studentService.getregular());
        assertEquals("India", studentService.getCountries());

        // Array verification
        assertArrayEquals(new String[]{"Math", "Science"}, studentService.getSubjects());
        assertEquals(2, studentService.getSubjects().length);
    }

    @Test
    void testActivateWithNulls() {
        // Test how the service behaves with missing/default properties
        context.registerInjectActivateService(studentService);

        // Depending on your Interface defaults, these might be null or default Java values
        assertEquals("Satyam",studentService.getStudentName());
        assertEquals(3, studentService.getRollNumber());
        assertTrue(studentService.getregular());
    }
}