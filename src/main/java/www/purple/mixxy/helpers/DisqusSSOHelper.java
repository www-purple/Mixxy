package www.purple.mixxy.helpers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DisqusSSOHelper {

  // User data, replace values with authenticated user data
  private HashMap<String, String> message = new HashMap<String, String>();

  public String base64EncodedStr;
  public String signature;
  public long timestamp;

  public DisqusSSOHelper(String id, String username, String email, String avatar,
      String disqusSecret) {

    try {

      message.put("id", id);
      message.put("username", username);
      message.put("email", email);
      message.put("avatar", avatar); // User's avatar URL (optional)
      // message.put("url","http://example.com/"); // User's website or profile URL (optional)

      // Encode user data
      ObjectMapper mapper = new ObjectMapper();

      String jsonMessage = mapper.writeValueAsString(message);

      base64EncodedStr = new String(Base64.encodeBase64(jsonMessage.getBytes()));

      // Get the timestamp
      timestamp = System.currentTimeMillis() / 1000;

      // Assemble the HMAC-SHA1 signature
      signature = calculateRFC2104HMAC(base64EncodedStr + " " + timestamp, disqusSecret);

      // Output string to use in remote_auth_s3 variable
      System.out.println(base64EncodedStr + " " + signature + " " + timestamp);

    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    catch (InvalidKeyException e) {
      e.printStackTrace();
    }
    catch (SignatureException e) {
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  private static String toHexString(byte[] bytes) {
    @SuppressWarnings("resource")
    Formatter formatter = new Formatter();
    for (byte b : bytes) {
      formatter.format("%02x", b);
    }

    return formatter.toString();
  }

  public static String calculateRFC2104HMAC(String data, String key)
      throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
    final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
    Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
    mac.init(signingKey);
    return toHexString(mac.doFinal(data.getBytes()));
  }

}
