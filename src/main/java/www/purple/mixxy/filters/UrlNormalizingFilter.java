package www.purple.mixxy.filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

/**
 * Any URLs that almost follow a specific convention, but not quite, will be
 * fixed and the user redirected to the proper URL. This is for SEO purposes;
 * {@code /resource} and {@code /resource/} (note the trailing slash) are two
 * separate Web pages, as far as Google is concerned.
 * 
 * This {@code Filter} transforms URLs in the following ways:
 * <ul>
 * <li>Strips trailing slashes</li>
 * </ul>
 * 
 * @author Jesse Talavera-Greenberg
 *
 */
public class UrlNormalizingFilter implements Filter {

  @Override
  public Result filter(FilterChain filterChain, Context context) {

    String path = context.getRequestPath();
    boolean transformed = false;

    if (path.endsWith("/")) {
      transformed = true;
      path = path.substring(0, path.length() - 1);
    }

    if (transformed) {

      return Results.redirect(path);
    }

    return transformed ? Results.redirect(path) : filterChain.next(context);
    // TODO: Context might be lost!
  }

}
