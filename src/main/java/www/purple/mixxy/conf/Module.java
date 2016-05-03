package www.purple.mixxy.conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import ninja.appengine.AppEngineModule;

import www.purple.mixxy.helpers.ApiKeys;

/**
 * {@linkplain AbstractModule#bind Bind}s objects for injection and {@link #install}s other
 * {@linkplain com.google.inject.Module module}s.
 */
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
