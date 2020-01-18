/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.service;

import io.terrible.thumbnail.creator.utils.CommandUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class ThumbnailServiceImpl implements ThumbnailService {

  private final ProcessService processService;

  /**
   * Given a video file, divide its time length into the number of thumbnails to create, using
   * FFMPEG jump to those time stamps to grab the closest frame we find.
   */
  @Override
  public ArrayList<Path> createThumbnails(final Path videoPath, final int thumbnailCount) {

    final double duration = calculateDuration(videoPath) / 60;

    final Path directory = createThumbnailDirectory(videoPath);

    final ArrayList<Path> thumbnails = new ArrayList<>(thumbnailCount);

    for (int i = 1; i <= thumbnailCount; i++) {
      final String output = String.format("%s/00%d.jpg", directory, i);

      final double timestamp = (i - 0.5) * (duration / thumbnailCount) * 60;

      try {
        processService.execute(
            CommandUtil.createThumbnail(
                String.valueOf(timestamp), videoPath.toFile().getAbsolutePath(), output));

        thumbnails.add(Paths.get(output));
      } catch (final IOException | InterruptedException e) {
        log.error("failed to create thumbnail {} {} {}", videoPath, e.getMessage(), e);
      }
    }

    return thumbnails;
  }

  /**
   * Use FFMPEG to calculate the total duration of the video. This is used to work out where to
   * create the thumbnails.
   */
  private double calculateDuration(final Path videoPath) {

    try {
      final String output =
          processService.execute(
              CommandUtil.calculateDuration(videoPath.toFile().getAbsolutePath()));

      return Double.parseDouble(output);

    } catch (final IOException | InterruptedException e) {
      log.error("failed to calculate duration {} {} {}", videoPath, e.getMessage(), e);

      return -1;
    }
  }

  /**
   * Creates a folder at the same directory of the video. Warning: This will also remove old copies
   * of the thumbnails if any are found
   */
  private Path createThumbnailDirectory(final Path videoPath) {

    final Path path = Paths.get(videoPath.getParent().toFile().getAbsolutePath() + "/.thumbnails");

    // Not worried if this fails, just try and clean out any old thumbnails before creating new ones
    FileUtils.deleteQuietly(path.toFile());

    try {
      return Files.createDirectories(path);
    } catch (final IOException e) {
      log.error("failed to create directory {} {} {}", videoPath, e.getMessage(), e);
    }

    return null;
  }
}
