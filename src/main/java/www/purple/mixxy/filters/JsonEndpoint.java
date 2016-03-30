package www.purple.mixxy.filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

/**
 * Filter for controllers that serve as both Web pages and JSON endpoints. This
 * way we can have an API without having to implement extra controllers or
 * controller methods. After all, if curl and Google Chrome are asking for the
 * same data, why not give it to them with the same code?
 * 
 * When using this to annotate a class or method, remember to provide a view to
 * use as well.
 * 
 * @author Jesse Talavera-Greenberg
 */
public class JsonEndpoint implements Filter {

  @Override
  public Result filter(FilterChain filterChain, Context context) {

    Result result = filterChain.next(context);

    if (context.getRequestPath().startsWith("/api")) {
      // If the client is asking for API access...
      return result.contentType(Result.APPLICATION_JSON);

      // TODO: Is there a better way to tell if a URL refers to an API endpoint
      // than by checking the string prefix?
    } else {
      return result;
    }
  }

}
