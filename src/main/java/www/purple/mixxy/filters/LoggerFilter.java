/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package www.purple.mixxy.filters;

import java.util.logging.Level;

import com.google.inject.Inject;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

import org.slf4j.Logger;

/**
 * Logs a request in level {@link Level#INFO}.
 *
 * <p>
 * {@link Filter}s can be attached to classes or methods via {@code @FilterWith(LoggerFilter.class)}
 * </p>
 *
 * @author ra
 */
public class LoggerFilter implements Filter {

  private final Logger logger;

  @Inject
  public LoggerFilter(Logger logger) {
    this.logger = logger;
  }

  @Override
  public Result filter(FilterChain chain, Context context) {

    logger.info("Got request from: %s", context.getRequestPath());
    return chain.next(context);
  }

}
