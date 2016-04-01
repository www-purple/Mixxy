package www.purple.mixxy.filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

public class UrlNormalizingFilter implements Filter {

  @Override
  public Result filter(FilterChain filterChain, Context context) {

    String path = context.getRequestPath();

    if (path.endsWith("/")) {
      return Results.redirect(path.substring(0, path.length() - 1));
    }
    
    return filterChain.next(context);
  }

}
