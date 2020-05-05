/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.service;

import io.terrible.thumbnail.creator.utils.CommandUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

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
  public ArrayList<String> createThumbnails(
      final Path input, final Path output, final int thumbnailCount) {

    final double duration = calculateDuration(input) / 60;

    final ArrayList<String> thumbnails = new ArrayList<>(thumbnailCount);

    for (int i = 1; i <= thumbnailCount; i++) {
      final Path thumbnailLocation = Path.of(String.format("%s/00%d.jpg", output, i));

      final double timestamp = (i - 0.5) * (duration / thumbnailCount) * 60;

      try {
        processService.execute(
            CommandUtil.createThumbnail(
                String.valueOf(timestamp),
                input.toFile().getAbsolutePath(),
                thumbnailLocation.toString()));

        thumbnails.add(thumbnailLocation.toString());
      } catch (final IOException | InterruptedException e) {
        log.error("failed to create thumbnail {} {} {}", input, e.getMessage(), e);
      }
    }

    return thumbnails;
  }

  /**
   * Use FFMPEG to calculate the total duration of the video. This is used to work out where to
   * create the thumbnails.
   */
  private double calculateDuration(final Path input) {

    try {
      final String output =
          processService.execute(CommandUtil.calculateDuration(input.toFile().getAbsolutePath()));

      return Double.parseDouble(output);

    } catch (final IOException | InterruptedException e) {
      log.error("failed to calculate duration {} {} {}", input, e.getMessage(), e);

      return -1;
    }
  }
}
