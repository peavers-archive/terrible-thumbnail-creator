/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageBinding {

    String THUMBNAIL_CHANNEL = "thumbnailChannel";

    @Output(THUMBNAIL_CHANNEL)
    MessageChannel subscription();
}
