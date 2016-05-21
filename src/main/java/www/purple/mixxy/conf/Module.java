package www.purple.mixxy.conf;

import ninja.appengine.AppEngineModule;

import com.googlecode.objectify.Objectify;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import www.purple.mixxy.helpers.ApiKeys;

@Singleton
public class Module extends AbstractModule {

  @Override
  protected void configure() {
    GcsService service = GcsServiceFactory.createGcsService(new RetryParams.Builder()
        .initialRetryDelayMillis(10)
        .retryMaxAttempts(10)
        .totalRetryPeriodMillis(15000)
        .build());

    bind(ApiKeys.class);
    bind(StartupActions.class);
    bind(Objectify.class).toProvider(ObjectifyProvider.class);
    bind(GcsService.class).toInstance(service);
    install(new AppEngineModule());

  }

}
