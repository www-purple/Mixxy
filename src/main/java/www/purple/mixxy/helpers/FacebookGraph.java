package www.purple.mixxy.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

public class FacebookGraph {
  private String accessToken;

  public FacebookGraph(String accessToken) {
    this.accessToken = accessToken;
  }

  public FacebookAuthResponse getFBGraph() {
    FacebookAuthResponse far = null;

    try {
      URL url = new URL("https://graph.facebook.com/me?access_token=" + accessToken);
      URLConnection conn1 = url.openConnection();
      String line = "", outputString = "";
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
      while ((line = reader.readLine()) != null) {
        outputString += line;
      }
      reader.close();
      far = new Gson().fromJson(outputString, FacebookAuthResponse.class);

    }
    catch (MalformedURLException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return far;
  }
}
