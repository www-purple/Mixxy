/**
 * Copyright (C) 2012 the original author or authors.
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

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.Param;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.helpers.FacebookAuthHelper;
import www.purple.mixxy.helpers.FacebookAuthResponse;
import www.purple.mixxy.helpers.FacebookGraph;
import www.purple.mixxy.helpers.GoogleAuthHelper;
import www.purple.mixxy.helpers.GoogleUser;
import www.purple.mixxy.helpers.OAuthProviders;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class LoginLogoutController {
    
    @Inject
    private UserDao userDao;
    
    @Inject
    private Logger logger;

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
    	context.getCookies().clear();
        context.getSession().clear();
        context.getFlashScope().success("login.logoutSuccessful");

        return Results.redirect("/");

    }

	///////////////////////////////////////////////////////////////////////////
	// Login
	///////////////////////////////////////////////////////////////////////////
    public Result login(@Param("provider") String provider) {   

    	switch(provider)
    	{
	    	case OAuthProviders.GOOGLE:
	    		GoogleAuthHelper gglHelper = new GoogleAuthHelper();
		    	return Results.redirect(gglHelper.buildLoginUrl());
	    	case OAuthProviders.FACEBOOK:
	    		FacebookAuthHelper fbHelper = new FacebookAuthHelper();
		    	return Results.redirect(fbHelper.getFBAuthUrl());
	    	case OAuthProviders.DEVIANTART:
	    		return Results.TODO();
	    	default:
	    		return Results.redirect("/");
    	}
    	
    }
    
    public Result validate(
    		@Param("state") String state,
    		@Param("code") String code,
    		Context context) {
    	
    	// TODO: find a way to pass and get facebook state
    	if(state == null)
    		return validateFacebookResponse("facebook", code, context);
    	else
    		return validateGoogleResponse("google", code, context);
    }
    
    public Result validateGoogleResponse(String provider, String code, Context context) {
    	
    	if (code == null || code.equals("")) {
    		logger.error("User cancelled google auth or no code returned");
			return loginError(context);
		}
    	
    	GoogleAuthHelper helper = new GoogleAuthHelper();
    	String data;
    	
    	// Get json response
    	try {
    		data = helper.getUserInfoJson(code);
    		
    		System.out.println(data);
    	} catch (IOException e) {
    		logger.error("Cannot get Google User Info", e);
    		return loginError(context);
    	}
    	
    	// Parse json response
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		@SuppressWarnings("unchecked")
    		Map<String,Object> user = mapper.readValue(data, Map.class);
    		
    		// Validate user
        	if(userDao.isUserValid((String)user.get(GoogleUser.EMAIL))) {
        		// If user exists
        		// Start session
			    newSession((String)user.get(GoogleUser.EMAIL), context);
        	}
        	else
        	{
        		// Create new user
				userDao.createUser(
						(String)user.get(GoogleUser.EMAIL),
						(String)user.get(GoogleUser.FIRST_NAME),
						(String)user.get(GoogleUser.LAST_NAME),
						(String)user.get(GoogleUser.GENDER),
						(String)user.get(GoogleUser.EMAIL),
						(String)user.get(GoogleUser.PICTURE_URL),
						(String)user.get(GoogleUser.LOCALE),
						(String)user.get(GoogleUser.ID),
						provider);
				
				// Start session
				newSession((String)user.get(GoogleUser.EMAIL), context);
				
		        // TODO: Redirect to profile
				return Results.redirect("/privacy");
        	}
    		
		} catch (JsonGenerationException e) {
            logger.error("Invalid JSON response from Google", e);
            return loginError(context);
		} catch (JsonMappingException e) {
            logger.error("Cannot map JSON", e);
            return loginError(context);
		} catch (IOException e) {
			logger.error("IO Error", e);
			return loginError(context);
		}

    	return Results.redirect("/");
    }
    
    private Result validateFacebookResponse(String provider, String code, Context context) {
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		
		FacebookAuthHelper fbhelper = new FacebookAuthHelper();
		String accessToken = fbhelper.getAccessToken(code);

		FacebookGraph fbGraph = new FacebookGraph(accessToken);
		FacebookAuthResponse far = fbGraph.getFBGraph();
		
		return Results.json().render(far);
	}
	
	public void newSession(String username, Context context) {
		context.getSession().put("username", username);
		context.getFlashScope().success("login.loginSuccessful");
	}
	
	public Result loginError(Context context) {
		context.getFlashScope().error("login.adminError");
		return Results.redirect("/");
	}
}
