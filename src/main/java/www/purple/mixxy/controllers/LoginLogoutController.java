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
import ninja.utils.NinjaProperties;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.helpers.ApiKeys;
import www.purple.mixxy.helpers.DeviantArtAuthHelper;
import www.purple.mixxy.helpers.FacebookAuthHelper;
import www.purple.mixxy.helpers.FacebookUser;
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

import com.google.appengine.api.utils.SystemProperty;
import www.purple.mixxy.models.User;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class LoginLogoutController {
    
    @Inject
    private Logger logger;
    
    @Inject
    private UserDao userDao;
    
    @Inject
    private ApiKeys apiKeys;
    
    @Inject
    private NinjaProperties ninjaProperties;
    
    private String callbackURI;

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
    	
        String appVersion = SystemProperty.applicationVersion.get();   
        callbackURI = ninjaProperties.get("callback.uri");     
        callbackURI = callbackURI.replace("#", appVersion.substring(0, appVersion.indexOf('.')));

    	switch(provider)
    	{
	    	case OAuthProviders.GOOGLE:
	    		GoogleAuthHelper gglHelper = new GoogleAuthHelper(apiKeys.getGoogleId(), apiKeys.getGoogleSecret(), callbackURI);
		    	return Results.redirect(gglHelper.buildLoginUrl());
	    	case OAuthProviders.FACEBOOK:
	    		FacebookAuthHelper fbHelper = new FacebookAuthHelper(apiKeys.getFacebookId(), apiKeys.getFacebookSecret(), callbackURI);
		    	return Results.redirect(fbHelper.buildLoginUrl());
	    	case OAuthProviders.DEVIANTART:
	    		DeviantArtAuthHelper daHelper = new DeviantArtAuthHelper(apiKeys.getDeviantartId(), apiKeys.getDeviantartKey(), callbackURI);
	    		return Results.redirect(daHelper.buildLoginUrl());
	    	default:
	    		return Results.redirect("/");
    	}
    	
    }
    
    public Result validate(
    		@Param("state") String state,
    		@Param("code") String code,
    		Context context) {
    	
    	System.out.println("State: " + state + " Code: " + code);
    	
    	if(state.contains(OAuthProviders.FACEBOOK))
    		return validateFacebookResponse(OAuthProviders.FACEBOOK, code, context);
    	else if(state.contains(OAuthProviders.GOOGLE))
    		return validateGoogleResponse(OAuthProviders.GOOGLE, code, context);
    	else if(state.contains(OAuthProviders.DEVIANTART))
    		return validateDeviantartResponse(OAuthProviders.DEVIANTART, code, context);
    	else
    		return Results.redirect("/terms");
    }
    
    public Result validateGoogleResponse(String provider, String code, Context context) {
    	
    	if (code == null || code.equals("")) {
    		logger.error("User cancelled google auth or no code returned");
			return loginError(context);
		}
    	
    	GoogleAuthHelper helper = new GoogleAuthHelper(apiKeys.getGoogleId(), apiKeys.getGoogleSecret(), callbackURI);
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
    		logger.error("User cancelled facebook auth or no code returned");
			return loginError(context);
		}
    	
    	// Exchange code for an access token
    	FacebookAuthHelper helper = new FacebookAuthHelper(apiKeys.getFacebookId(), apiKeys.getFacebookSecret(), callbackURI);
    	String data = helper.getUserInfoJson(code);
    	
    	// Parse json response
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		@SuppressWarnings("unchecked")
    		Map<String,Object> user = mapper.readValue(data, Map.class);
    		
    		// Validate user
        	if(userDao.isUserValid((String)user.get(FacebookUser.EMAIL))) {
        		// If user exists
        		// Start session
			    newSession((String)user.get(FacebookUser.EMAIL), context);
        	}
        	else
        	{
        		@SuppressWarnings("unchecked")
	    		Map<String,Object> picture = (Map<String, Object>) user.get("picture");
			    @SuppressWarnings("unchecked")
				Map<String,Object> pictureUrl = (Map<String, Object>) picture.get("data");
			    
        		// Create new user
				userDao.createUser(
						(String)user.get(FacebookUser.EMAIL),
						(String)user.get(FacebookUser.FIRST_NAME),
						(String)user.get(FacebookUser.LAST_NAME),
						(String)user.get(FacebookUser.GENDER),
						(String)user.get(FacebookUser.EMAIL),
						(String)pictureUrl.get(FacebookUser.PICTURE_URL),
						(String)user.get(FacebookUser.LOCALE),
						(String)user.get(FacebookUser.ID),
						provider);
				
				// Start session
				newSession((String)user.get(FacebookUser.EMAIL), context);
				
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
    
    private Result validateDeviantartResponse(String provider, String code, Context context) {
		
    	if (code == null || code.equals("")) {
    		logger.error("User cancelled deviantart auth or no code returned");
			return loginError(context);
		}
    	
    	// Exchange code for an access token
    	DeviantArtAuthHelper helper = new DeviantArtAuthHelper(apiKeys.getDeviantartId(), apiKeys.getDeviantartKey(), ninjaProperties.get("callback.uri"));
    	String data = helper.getAccessToken(code);
    	
    	System.out.println(code);

    	return Results.redirect("/terms");
	}
	
	public void newSession(String username, Context context) {
		// here???????
		User user = userDao.getUser(username);
		if (user == null) {
			context.getFlashScope().error("Invalid user.");
			loginError(context);
		}
		context.getSession().put("username", user.username);
		context.getSession().put("userNickname", user.firstname);
		context.getSession().put("userAvatar", user.pictureUrl);
		context.getFlashScope().success("login.loginSuccessful");
	}
	
	public Result loginError(Context context) {
		context.getFlashScope().error("login.adminError");
		return Results.redirect("/");
	}
}
