package www.purple.mixxy.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FacebookAuthHelper {

	private String callbackURI;
	private String clientId;
	private String clientSecret;
	private String stateToken;
	
	public FacebookAuthHelper(String clientId, String clientSecret, String callbackURI) {
		
		this.callbackURI = callbackURI;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		
		generateStateToken();
	}
	
	/**
	 * Builds a login URL based on app ID, secret, callback URI, and scope
	 */
	public String buildLoginUrl() {
		String url = "";
		try {
			url = "http://www.facebook.com/dialog/oauth?" + 
				"client_id=" + clientId + "&redirect_uri=" + URLEncoder.encode(callbackURI, "UTF-8") +
				"&state=" + stateToken + "&scope=public_profile";
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * Generates a secure state token 
	 */
	private void generateStateToken(){
		SecureRandom sr1 = new SecureRandom();
		stateToken = "facebook;" + sr1.nextInt();
	}
	
	private String getFacebookGraphUrl(String code) {
		String url = "";
		try {
			url = "https://graph.facebook.com/v2.4/oauth/access_token?" +
				"client_id=" + clientId + "&redirect_uri=" + URLEncoder.encode(callbackURI, "UTF-8") +
				"&client_secret=" + clientSecret + "&code=" + code;
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private String getAccessToken(String code) {
		String accessToken = null;
		String strTemp = "";
		String response = "";
		
		try {
			URL url = new URL(getFacebookGraphUrl(code));
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			while (null != (strTemp = br.readLine())) {
				response += strTemp;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		@SuppressWarnings("unchecked")
    		Map<String,Object> user = mapper.readValue(response, Map.class);
    		
    		accessToken = (String) user.get("access_token");
    	} catch (JsonGenerationException e) {
            e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    return accessToken;
	}
	
	public String getUserInfoJson(final String code) {
		String accessToken = getAccessToken(code);
		String line = "";
		String json = "";
		
		try {
			URL url = new URL("https://graph.facebook.com/me?"
					+ "fields=first_name,last_name,picture,email,birthday,gender,locale"
					+ "&access_token=" + accessToken);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			while (null != (line = br.readLine())) {
				json += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    String userJson = StringEscapeUtils.unescapeJava(json);

		return userJson;
	}
}