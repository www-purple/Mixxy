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
import www.purple.mixxy.conf.ObjectifyProvider;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.helpers.FacebookAuthHelper;
import www.purple.mixxy.helpers.FacebookAuthResponse;
import www.purple.mixxy.helpers.FacebookGraph;
import www.purple.mixxy.helpers.GoogleAuthHelper;
import www.purple.mixxy.helpers.GoogleAuthResponse;
import www.purple.mixxy.helpers.OAuthProviders;
import www.purple.mixxy.models.User;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class LoginLogoutController {
    
    @Inject
    private Logger logger;
    
    @Inject
    private UserDao userDao;

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
        context.getSession().clear();
        context.getFlashScope().success("login.logoutSuccessful");

        return Results.redirect("/");

    }

	///////////////////////////////////////////////////////////////////////////
	// Login
	///////////////////////////////////////////////////////////////////////////
    public Result login(@Param("provider") String provider) {
    	
    	if(provider.equals(OAuthProviders.GOOGLE)) {
			GoogleAuthHelper gglHelper = new GoogleAuthHelper();
	    	return Results.redirect(gglHelper.buildLoginUrl());
    	} else {
			FacebookAuthHelper fbHelper = new FacebookAuthHelper();
	    	return Results.redirect(fbHelper.getFBAuthUrl());
    	}
    	
    }
    
    public Result validate(
    		@Param("state") String state,
    		@Param("code") String code,
    		Context context) {
    	
    	if(state == null)
    		return validateFacebookAuth("facebook", code, context);
    	else
    		return validateGoogleAuth("google", code, context);
    }
    
    private Result validateFacebookAuth(String provider, String code, Context context) {
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback (got null or empty string)");
		}
		
		FacebookAuthHelper fbhelper = new FacebookAuthHelper();
		String accessToken = fbhelper.getAccessToken(code);

		FacebookGraph fbGraph = new FacebookGraph(accessToken);
		FacebookAuthResponse far = fbGraph.getFBGraph();
		
		return Results.json().render(far);
	}

	public Result validateGoogleAuth(String provider, String code, Context context) {
    	GoogleAuthHelper helper = new GoogleAuthHelper();
    	String data = "empty";
    	
    	try {
			data = helper.getUserInfoJson(code);
		} catch (IOException e) {
			logger.error("Cannot get Google authN info", e);
		}
    	
    	ObjectMapper mapper = new ObjectMapper();
		try {

			// Convert JSON string to Object
			GoogleAuthResponse userdata = mapper.readValue(data, GoogleAuthResponse.class);
			
			// At this point we can login or signup user
			String email = userdata.getEmail();
			String username = email.substring(0, email.indexOf('@'));
			boolean areCredentialsValid = userDao.isUserValid(username);
			
			if(areCredentialsValid) {
				context.getSession().put(User.USERNAME, userdata.getEmail());
				context.getFlashScope().success("login.loginSuccessful");
			} else {
				// Create new user
				ObjectifyProvider objectifyProvider = new ObjectifyProvider();
		        Objectify ofy = objectifyProvider.get();
		        
		        // Retrieve the user with e-mail address
		        User user = ofy.load().type(User.class).filter("username", userdata.getEmail()).first().now();
		        
		        //	Check if the User already exists in the Datastore
		        //	if not, create that user, otherwise use the User instance
		        if(user == null) {
		        	
		        	// Create a new user and save it
			        user = new User(userdata.getEmail(), userdata.getGiven_name(), userdata.getFamily_name(), userdata.getEmail(),
			        		userdata.getPicture(), userdata.getLocale(), provider, userdata.getId());
			        ofy.save().entity(user).now();
			        
		        }
		             
		        context.getSession().put("username", userdata.getEmail());
				context.getFlashScope().success("login.loginSuccessful");
				
		        // Redirect to profile
				return Results.redirect("/privacy");
			}
		} catch (JsonGenerationException e) {
            logger.error("Invalid JSON response from Google", e);
		} catch (JsonMappingException e) {
            logger.error("Cannot map JSON", e);
		} catch (IOException e) {
            logger.error("IO Error", e);
		}
		// TODO: Handle these errors
		
		return Results.redirect("/");
    }
}
