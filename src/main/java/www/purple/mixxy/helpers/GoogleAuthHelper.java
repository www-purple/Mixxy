package www.purple.mixxy.helpers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

public final class GoogleAuthHelper {

  // start google authentication constants
  private static final Collection<String> SCOPE = Arrays.asList("profile;email".split(";"));
  private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  // end google authentication constants

  private String callbackURI;
  private String stateToken;

  private final GoogleAuthorizationCodeFlow flow;

  /**
   * Constructor initializes the Google Authorization Code Flow with CLIENT ID, SECRET, and SCOPE
   */
  public GoogleAuthHelper(String clientId, String clientSecret, String callbackURI) {

    this.callbackURI = callbackURI;

    flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
        JSON_FACTORY, clientId, clientSecret, SCOPE).build();

    generateStateToken();
  }

  /**
   * Builds a login URL based on client ID, secret, callback URI, and scope
   */
  public String buildLoginUrl() {

    final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();

    return url.setRedirectUri(callbackURI).setState(stateToken).build();
  }

  /**
   * Generates a secure state token
   */
  private void generateStateToken() {

    SecureRandom sr1 = new SecureRandom();

    stateToken = "google;" + sr1.nextInt();

  }

  /**
   * Accessor for state token
   */
  public String getStateToken() {
    return stateToken;
  }

  /**
   * Expects an Authentication Code, and makes an authenticated request for the user's profile
   * information
   * 
   * @return JSON formatted user profile information
   * @param authCode
   *          authentication code provided by google
   */
  public String getUserInfoJson(final String authCode) throws IOException {

    final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(callbackURI)
        .execute();
    final Credential credential = flow.createAndStoreCredential(response, null);
    final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
    // Make an authenticated request
    final GenericUrl url = new GenericUrl(USER_INFO_URL);
    final HttpRequest request = requestFactory.buildGetRequest(url);
    request.getHeaders().setContentType("application/json");
    final String jsonIdentity = request.execute().parseAsString();

    return jsonIdentity;

  }
}
