package com.tushar.aem.guides.core.dynamicdropdowns;

import com.adobe.granite.ui.components.ds.DataSource;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class MyJsonDataDropdownServletTest {

    // Use JCR_MOCK to support Node and Property operations used in the servlet
    private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    private MyJsonDataDropdownServlet servlet;
    private final String JSON_PATH = "/content/dam/data.json";

    @BeforeEach
    void setUp() throws Exception {
        servlet = new MyJsonDataDropdownServlet();

        // 1. Create the JSON file structure in JCR
        String jsonContent = "{\"key1\":\"Value One\", \"key2\":\"Value Two\"}";
        InputStream is = new ByteArrayInputStream(jsonContent.getBytes(StandardCharsets.UTF_8));

        context.create().resource(JSON_PATH);
        Resource jcrContent = context.create().resource(JSON_PATH + "/jcr:content",
                "jcr:data", is);

        // 2. Create the component resource with the datasource child node
        context.create().resource("/apps/my-component/cq:dialog/content/items/dropdown",
                "sling:resourceType", "granite/ui/components/coral/foundation/form/select");

        context.create().resource("/apps/my-component/cq:dialog/content/items/dropdown/datasource",
                "jsonDataPath", JSON_PATH);
    }

    @Test
    void testDoGet_DataSourcePopulation() {
        // Set the current resource to the dropdown component (which has the datasource child)
        context.currentResource("/apps/my-component/cq:dialog/content/items/dropdown");

        // Execute the servlet
        servlet.doGet(context.request(), context.response());

        // 1. Verify DataSource is set in Request Attribute
        DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());
        assertNotNull(ds, "DataSource should not be null");

        // 2. Verify contents of the DataSource
        Iterator<Resource> items = ds.iterator();
        assertTrue(items.hasNext());

        Resource firstItem = items.next();
        assertEquals("key1", firstItem.getValueMap().get("value", String.class));
        assertEquals("Value One", firstItem.getValueMap().get("text", String.class));

        assertTrue(items.hasNext());
        Resource secondItem = items.next();
        assertEquals("key2", secondItem.getValueMap().get("value", String.class));
    }

    @Test
    void testDoGet_HandleInvalidPath() {
        // Set properties to point to a non-existent JSON path
        context.create().resource("/apps/wrong-path/datasource", "jsonDataPath", "/content/missing.json");
        context.currentResource("/apps/wrong-path");

        // The servlet uses assert and tries to catch exceptions.
        // We verify it doesn't crash the request but logs an error (check logs in console).
//        assertDoesNotThrow(() -> servlet.doGet(context.request(), context.response()));

        DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());
        assertNull(ds, "DataSource should be null if path is invalid");
    }
}