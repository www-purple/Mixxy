package www.purple.mixxy.filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Route;

/**
 * Filter that makes a controller method double as a Web endpoint (for users) and a JSON endpoint (for data). This way
 * we can have an API without having to implement extra controllers or controller methods. After all, if {@code curl}
 * and Google Chrome are asking for the same data, why not give it to them with the same code?
 *
 * <p>
 * If the pattern for a {@link Route} to an endpoint with this annotation begins with {@code "/api/"}, the endpoint
 * method will return JSON. Otherwise, this {@link Filter} does nothing.
 * </p>
 *
 * <p>
 * When writing a method annotated with this {@link Filter}, pass the relevant data to the {@link Result} object and let
 * the view do the rest!
 * </p>
 *
 * @author Jesse Talavera-Greenberg
 */
public class JsonEndpoint implements Filter {

  @Override
  public Result filter(FilterChain filterChain, Context context) {

    Result result = filterChain.next(context);

    if (context.getRequestPath().startsWith("/api/")) {
      // If the client is asking for API access...
      return result.json();

      // TODO: Is there a better way to tell if a URL refers to an API endpoint
      // than by checking the string prefix?
    }
    else {
      return result;
    }
  }

}
