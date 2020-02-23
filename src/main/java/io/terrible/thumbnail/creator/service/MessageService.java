/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.service;

import java.nio.file.Path;
import java.util.ArrayList;
import org.springframework.messaging.support.GenericMessage;

/** @author Chris Turner (chris@forloop.space) */
public interface MessageService {

  boolean send(GenericMessage<ArrayList<Path>> message);
}
