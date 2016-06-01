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
  private static final int INITIAL_RETRY_DELAY_MS = 10;
  private static final int RETRY_MAX_ATTEMPTS = 10;
  private static final int TOTAL_RETRY_PERIOD_MS = 15000;

  @Override
  protected void configure() {
    final GcsService service = GcsServiceFactory.createGcsService(new RetryParams.Builder()
        .initialRetryDelayMillis(INITIAL_RETRY_DELAY_MS)
        .retryMaxAttempts(RETRY_MAX_ATTEMPTS)
        .totalRetryPeriodMillis(TOTAL_RETRY_PERIOD_MS)
        .build());

    bind(ApiKeys.class);
    bind(StartupActions.class);
    bind(Objectify.class).toProvider(ObjectifyProvider.class);
    bind(GcsService.class).toInstance(service);
    install(new AppEngineModule());

  }

}
