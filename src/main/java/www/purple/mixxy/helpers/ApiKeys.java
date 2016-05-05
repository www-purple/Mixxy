package www.purple.mixxy.helpers;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.google.inject.Singleton;

import ninja.utils.SwissKnife;

@Singleton
public final class ApiKeys {
  private PropertiesConfiguration properties;

  private String googleId;
  private String googleSecret;

  private String facebookId;
  private String facebookSecret;
  
  private String deviantartId;
  private String deviantartKey;

  public ApiKeys() {
    properties = SwissKnife.loadConfigurationInUtf8("www/purple/mixxy/helpers/auth.properties");

    googleId = properties.getString("google.id");
    googleSecret = properties.getString("google.secret");
    facebookId = properties.getString("facebook.id");
    facebookSecret = properties.getString("facebook.secret");
    deviantartId = properties.getString("deviantart.id");
    deviantartKey = properties.getString("deviantart.secret");
  }

  public String getGoogleId() {
    return googleId;
  }

  public String getGoogleSecret() {
    return googleSecret;
  }

  public String getFacebookId() {
    return facebookId;
  }

  public String getFacebookSecret() {
    return facebookSecret;
  }

  public String getDeviantartId() {
	return deviantartId;
  }

  public String getDeviantartKey() {
	return deviantartKey;
  }
}
