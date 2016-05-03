package www.purple.mixxy.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class DeviantArtAuthHelper {

  private static final String CLIENT_ID = "4606";
  private static final String CLIENT_SECRET = "f61c2bd48549550afb55e9c3bbd5feef";
  private static final String CALLBACK_URI = "http://localhost:8080/validate";

  private String stateToken;

  public DeviantArtAuthHelper() {
    generateStateToken();
  }

  /**
   * Builds a login URL based on app ID, secret, callback URI, and scope
   */
  public String buildLoginUrl() {
    String url = "";
    try {
      url = "https://www.deviantart.com/oauth2/authorize?" +
          "response_type=code" + "&client_id=" + CLIENT_ID +
          "&redirect_uri=" + URLEncoder.encode(CALLBACK_URI, "UTF-8") +
          "&state=" + stateToken + "&scope=basic";
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return url;
  }

  /**
   * Generates a secure state token
   */
  private void generateStateToken() {
    SecureRandom sr1 = new SecureRandom();
    stateToken = "deviantart;" + sr1.nextInt();
  }

  private String getAccessTokenUrl(final String code) {
    String url = "";
    try {
      url = "https://www.deviantart.com/oauth2/token?" +
          "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET +
          "&grant_type=authorization_code" +
          "&redirect_uri=" + URLEncoder.encode(CALLBACK_URI, "UTF-8") +
          "&code=" + code;
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return url;
  }

  public String getAccessToken(final String code) {

    String accessToken = null;
    String strTemp = "";
    String response = "";

    try {
      URL url = new URL(getAccessTokenUrl(code));
      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

      while (null != (strTemp = br.readLine())) {
        response += strTemp;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    /*
     * ObjectMapper mapper = new ObjectMapper(); try {
     *
     * @SuppressWarnings("unchecked") Map<String,Object> user = mapper.readValue(response, Map.class);
     *
     * accessToken = (String) user.get("access_token"); } catch (JsonGenerationException e) { e.printStackTrace(); }
     * catch (JsonMappingException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
     */

    return response;
  }

  public String getUserInfoJson(final String code) {
    String accessToken = getAccessToken(code);
    /*
     * String line = ""; String json = "";
     *
     * try { URL url = new URL("https://www.deviantart.com/api/v1/oauth2/user/whoami?" + "access_token=" + accessToken);
     *
     * BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
     *
     * while (null != (line = br.readLine())) { json += line; } } catch (MalformedURLException e) { e.printStackTrace();
     * } catch (IOException e) { e.printStackTrace(); }
     *
     * String userJson = StringEscapeUtils.unescapeJava(json);
     */

    return accessToken;
  }

}
