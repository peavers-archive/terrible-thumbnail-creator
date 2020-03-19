/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.service;

import java.nio.file.Path;
import java.util.ArrayList;

/** @author Chris Turner (chris@forloop.space) */
public interface ThumbnailService {

  ArrayList<String> createThumbnails(Path videoPath, int thumbnailCount);
}
