/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.service;

import io.terrible.thumbnail.creator.domain.Result;
import org.springframework.messaging.support.GenericMessage;

/** @author Chris Turner (chris@forloop.space) */
public interface MessageService {

  boolean send(GenericMessage<Result> message);
}
