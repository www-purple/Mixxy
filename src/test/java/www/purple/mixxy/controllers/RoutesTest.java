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

import ninja.NinjaRouterTest;
import www.purple.mixxy.controllers.ApplicationController;

import org.junit.Test;

public class RoutesTest extends NinjaRouterTest {

    @Test
    public void testRouting() {
        
        startServerInProdMode();
        aRequestLike("GET", "/").isHandledBy(ApplicationController.class, "index");
        aRequestLike("GET", "/index").isHandledBy(ApplicationController.class, "index");
    }
    
    @Test
    public void testThatSetupIsNotAccessibleInProd() {
        
        startServerInProdMode();
        aRequestLike("GET", "/setup").isNotHandledBy(ApplicationController.class, "setup");
        
    }

}