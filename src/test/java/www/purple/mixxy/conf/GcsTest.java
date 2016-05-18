package www.purple.mixxy.conf;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.inject.Inject;
import com.google.inject.Injector;

import ninja.utils.NinjaProperties;
import ninja.NinjaAppengineBackendTest;
import ninja.NinjaRunner;
import ninja.NinjaTest;

@RunWith(NinjaRunner.class)
public class GcsTest extends NinjaAppengineBackendTest {
  @Inject
  private GcsService gcs;

  @Inject
  private NinjaProperties properties;

  private String bucket;
  private String imageDir;
  private GcsFileOptions fileOptions;
  private GcsFilename testFilename;
  private byte[] content;

  private static final String TEST_FILE = "test_file.txt";

  @Before
  public void setup() {
    this.bucket = properties.get("gcs.bucket");
    this.imageDir = properties.get("gcs.imagedir");
    this.fileOptions = GcsFileOptions.getDefaultInstance();
    this.testFilename = new GcsFilename(bucket, TEST_FILE);
    this.content = new byte[1024];
    new Random().nextBytes(this.content);
  }

  @After
  public void teardown() {
    try {
      gcs.delete(testFilename);
    } catch (IOException e) {
      // We're only interested in cleanup here, it's okay if this fails
      e.printStackTrace();
    }
  }

  @Test
  public void testGcsInjected() {
    assertNotNull(gcs);
  }

  @Test
  public void testBucketDefined() {
    assertNotNull(bucket);
    assertFalse(bucket.isEmpty());
  }

  @Test
  public void testImageDirDefined() {
    assertNotNull(imageDir);
    assertFalse(imageDir.isEmpty());
  }

  @Test
  public void testNotUsingProdBucketForTests() {
    assertNotEquals(bucket, "mixxy-1249.appspot.com");
  }

  @Test
  public void testStoreFile() throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(this.content);

    gcs.createOrReplace(testFilename, fileOptions, buffer);
    // fails if this throws an exception
  }

  @Test
  public void testStoreAndDeleteFile() throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(this.content);

    gcs.createOrReplace(testFilename, fileOptions, buffer);
    // fails if this throws an exception

    assertTrue(gcs.delete(testFilename));
  }

  @Test
  public void testStoreAndRetrieveFile() throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(this.content);

    gcs.createOrReplace(testFilename, fileOptions, buffer);
    // fails if this throws an exception

    GcsInputChannel file = gcs.openReadChannel(testFilename, 0);

    ByteBuffer output = ByteBuffer.allocate(this.content.length);
    file.read(output);

    assertEquals(buffer, output);
  }
}
