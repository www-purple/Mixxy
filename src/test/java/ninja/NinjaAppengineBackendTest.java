package ninja;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;

import ninja.NinjaTest;
import www.purple.mixxy.conf.ObjectifyProvider;

import java.io.Closeable;

public class NinjaAppengineBackendTest extends NinjaTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());
    
    private Closeable session;

    @Before
    public void setUp() {
        helper.setUp();
        session = ObjectifyService.begin();
        ObjectifyProvider.setup();
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
        session.close();
    }

}