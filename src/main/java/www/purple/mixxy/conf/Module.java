package www.purple.mixxy.conf;

import ninja.appengine.AppEngineModule;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.helpers.ApiKeys;

@Singleton
public class Module extends AbstractModule {
    
    @Override
    protected void configure() {
        bind(ApiKeys.class);
        bind(StartupActions.class);
        bind(Objectify.class).toProvider(ObjectifyProvider.class);
        install(new AppEngineModule());        
        
    }

}
