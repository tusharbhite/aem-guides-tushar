package com.tushar.aem.guides.core.dynamicdropdowns;

import com.adobe.granite.ui.components.ds.DataSource;
import com.day.cq.tagging.Tag;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class MyTagDropdownServletTest {

    private final AemContext context = new AemContext();
    private MyTagDropdownServlet servlet;
    private final String TAGS_ROOT = "/content/cq:tags";

    @BeforeEach
    void setUp() {
        servlet = new MyTagDropdownServlet();

        // Create the root tag
        context.create().resource(TAGS_ROOT, "jcr:primaryType", "cq:Tag");

        // Create child tags with titles
        context.create().resource(TAGS_ROOT + "/red",
                "jcr:primaryType", "cq:Tag",
                "jcr:title", "Bright Red");

        context.create().resource(TAGS_ROOT + "/blue",
                "jcr:primaryType", "cq:Tag",
                "jcr:title", "Ocean Blue");

        // Create datasource config
        context.create().resource("/apps/my-dropdown/datasource", "tagsPath", TAGS_ROOT);
        context.currentResource("/apps/my-dropdown");
    }

    @Test
    void testDoGet_DataSourcePopulation() {
        // Execute the servlet
        servlet.doGet(context.request(), context.response());

        // 1. Retrieve the DataSource from the request attribute
        DataSource dataSource = (DataSource) context.request().getAttribute(DataSource.class.getName());
        assertNotNull(dataSource, "DataSource attribute should be set");

        // 2. Verify the items in the DataSource
        Iterator<Resource> iterator = dataSource.iterator();
        assertTrue(iterator.hasNext(), "Should have at least one tag");

        // Verify First Tag (Red)
        Resource redItem = iterator.next();
        assertEquals("red:", redItem.getValueMap().get("value", String.class));
        assertEquals("Bright Red", redItem.getValueMap().get("text", String.class));

        // Verify Second Tag (Blue)
        assertTrue(iterator.hasNext());
        Resource blueItem = iterator.next();
        assertEquals("blue:", blueItem.getValueMap().get("value", String.class));
        assertEquals("Ocean Blue", blueItem.getValueMap().get("text", String.class));

        assertFalse(iterator.hasNext(), "Should only have two tags");
    }

    @Test
    void testDoGet_EmptyTags() {
        // Point to a tag path that exists but has no children
        context.create().tag("/content/cq:tags/empty");
        context.create().resource("/apps/empty-dropdown/datasource", "tagsPath", "/content/cq:tags/empty");
        context.currentResource("/apps/empty-dropdown");

        servlet.doGet(context.request(), context.response());

        DataSource dataSource = (DataSource) context.request().getAttribute(DataSource.class.getName());
        assertNotNull(dataSource);
        assertFalse(dataSource.iterator().hasNext(), "DataSource iterator should be empty");
    }
}