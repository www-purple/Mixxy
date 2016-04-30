package www.purple.mixxy.filters;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;

import com.google.inject.Inject;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

/**
 * Fixes URLs that almost (but not quite) follow a specific convention and redirects users to the correct URL. This is
 * for SEO purposes; {@code /resource} and {@code /resource/} (note the trailing slash) are two separate Web pages, as
 * far as Google is concerned.
 *
 * <p>
 * This {@code Filter} transforms URLs in the following ways:
 * <ul>
 * <li>Strips trailing slashes</li>
 * </ul>
 * </p>
 *
 * @author Jesse Talavera-Greenberg
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class UrlNormalizingFilter implements Filter {
  @Inject
  private Logger logger;

  @Override
  public Result filter(FilterChain filterChain, Context context) {

    String originalPath = context.getRequestPath();
    try {
      originalPath = new URI(originalPath).normalize().toString();
      // Normalize the request URI, if it's valid
    }
    catch (URISyntaxException e) {
      logger.info("Request to URL with invalid syntax \"{}\" (index {})", e.getInput(), e.getIndex());
      // TODO: Should strange-looking URIs just be an error? Must do research
    }

    String path = originalPath;
    boolean transformed = false;

    if (originalPath.endsWith("/") && !"/".equals(originalPath)) {
      // If this URL has a trailing slash, but is not the root...
      // NOTE: This does not handle /URLS/with?query=parameters&at=the end.
      transformed = true;
      path = originalPath.substring(0, path.length() - 1);
    }
    // TODO: Support query parameters and case-insensitivity

    if (transformed) {
      // If we normalized the URL at all...
      logger.debug("Incoming request for \"{}\", normalizing to \"{}\"", originalPath, path);
      return filterChain.next(context).redirect(path).status(Result.SC_301_MOVED_PERMANENTLY);
    }
    else {
      return filterChain.next(context);
    }

  }

}
