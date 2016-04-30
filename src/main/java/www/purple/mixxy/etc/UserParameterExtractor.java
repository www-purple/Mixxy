package www.purple.mixxy.etc;

import ninja.Context;
import ninja.params.ArgumentExtractor;
import ninja.session.Session;

/**
 * If the {@code user} is specified in the API call, use it. If not, get the logged in user through their session. If
 * that fails, return {@code null}.
 */
public class UserParameterExtractor implements ArgumentExtractor<String> {

  @Override
  public String extract(Context context) {
    Session session = context.getSession();

    return context.getParameter("user", session.get("user"));
  }

  @Override
  public Class<String> getExtractedType() {
    return String.class;
  }

  @Override
  public String getFieldName() {
    return "user";
  }

}
