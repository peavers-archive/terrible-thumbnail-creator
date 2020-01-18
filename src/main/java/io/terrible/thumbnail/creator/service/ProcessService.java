/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.service;

import java.io.IOException;
import java.util.List;

/** @author Chris Turner (chris@forloop.space) */
public interface ProcessService {

  String execute(List<String> command) throws IOException, InterruptedException;
}
