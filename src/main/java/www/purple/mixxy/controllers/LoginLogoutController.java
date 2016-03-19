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

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith(AppEngineFilter.class)
public class LoginLogoutController {
    
    @Inject
    private UserDao userDao;
    
    
    ///////////////////////////////////////////////////////////////////////////
    // Login
    ///////////////////////////////////////////////////////////////////////////
    public Result login(@SuppressWarnings("unused") Context context) {

        return Results.html();

    }

    public Result loginPost(@Param("username") String username,
                            @Param("password") String password,
                            Context context) {

        boolean areCredentialsValid = userDao.isUserAndPasswordValid(username, password);
        
        
        if (areCredentialsValid) {
            context.getSession().put("username", username);
            context.getFlashScope().success("login.loginSuccessful");
            
            return Results.redirect("/");
            
        } else {
            
            // something is wrong with the input or password not found.
            context.getFlashScope().put("username", username);
            context.getFlashScope().error("login.errorLogin");

            return Results.redirect("/login");
            
        }
        
    }

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
        context.getSession().clear();
        context.getFlashScope().success("login.logoutSuccessful");

        return Results.redirect("/");

    }

}
