package www.purple.mixxy.etc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ninja.params.WithArgumentExtractor;

/**
 * Some APIs are of the form {@code /api/user/something}, which does
 * {@code something} on the calling user (like retrieving their works). Said
 * user must be authenticated. Others offer the same functionality, but in the
 * form {@code /api/users/(user)/something}, where a user can be explicitly
 * specified. Annotate a {@code String user} parameter to a controller's method,
 * it won't matter.
 * 
 * @author Jesse Talavera-Greenberg
 *
 */
@WithArgumentExtractor(LoggedInUserExtractor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface UserParameter {
  /* annotation */
}
