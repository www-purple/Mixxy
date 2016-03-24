/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package www.purple.mixxy.controllers;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import ninja.NinjaTest;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

public class LoginLogoutControllerTest extends NinjaTest {
    
    private static final String ARTICLE_ROUTE = "article/new";
  
    private Map<String, String> headers;
    private Map<String, String> formParameters;
    
    @Before
    public void setup() {
        
        ninjaTestBrowser.makeRequest(getServerAddress() + "setup");

        headers = Maps.newHashMap();
        formParameters = Maps.newHashMap();
    }
    
    @Test
    public void testCredentialsNeededForArticle() {

      // /////////////////////////////////////////////////////////////////////
      // Test posting of article does not work without login
      // /////////////////////////////////////////////////////////////////////
      String response = ninjaTestBrowser.makeRequest(getServerAddress()
              + ARTICLE_ROUTE, headers);
      assertTrue(response.contains("forbidden"));
    }
    
    @Test
    public void testLoginAndPostArticle() {
      formParameters.put("username", "bob@gmail.com");
      formParameters.put("password", "secret");
      
      ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress()
          + "login", headers, formParameters);

      String response = ninjaTestBrowser.makeRequest(getServerAddress()
          + ARTICLE_ROUTE, headers);
  
      assertTrue(response.contains("New article"));
    }

    @Test
    public void testLoginLogout() {
        ninjaTestBrowser.makeRequest(getServerAddress() + "article/new", headers);

        formParameters.put("username", "bob@gmail.com");
        formParameters.put("password", "secret");

        ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress()
                + "login", headers, formParameters);

        // /////////////////////////////////////////////////////////////////////
        // Logout
        // /////////////////////////////////////////////////////////////////////
        ninjaTestBrowser.makeRequest(getServerAddress() + "logout", headers);

        // /////////////////////////////////////////////////////////////////////
        // Assert that posting of article does not work any more...
        // /////////////////////////////////////////////////////////////////////
        String response = ninjaTestBrowser.makeRequest(getServerAddress()
                + ARTICLE_ROUTE, headers);
        assertTrue(response.contains("forbidden"));
    }

}
