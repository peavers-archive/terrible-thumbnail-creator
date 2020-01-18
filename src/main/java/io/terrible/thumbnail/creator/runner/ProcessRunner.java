/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.runner;

import io.terrible.thumbnail.creator.service.ThumbnailService;
import java.nio.file.Path;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@RequiredArgsConstructor
public class ProcessRunner implements CommandLineRunner {

  private final ThumbnailService ffmpegService;

  private final Path videoPath;

  @Override
  public void run(final String... strings) {

    final ArrayList<Path> thumbnails = ffmpegService.createThumbnails(videoPath);

    log.info("Created {}", thumbnails.toString());
  }
}
