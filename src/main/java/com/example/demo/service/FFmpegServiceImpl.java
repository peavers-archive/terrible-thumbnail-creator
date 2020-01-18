/* Licensed under Apache-2.0 */
package com.example.demo.service;

import com.example.demo.utils.Commands;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class FFmpegServiceImpl implements FFmpegService {

  private static final int NUMBER_OF_THUMBNAILS = 12;

  private final ProcessService processService;

  /**
   * Given a video file, divide its time length into the number of thumbnails to create, using
   * FFMPEG jump to those time stamps to grab the closest frame we find.
   */
  @Override
  public ArrayList<Path> createThumbnails(final Path videoPath) {
    final double duration = calculateDuration(videoPath) / 60;

    final ArrayList<Path> thumbnails = new ArrayList<>(NUMBER_OF_THUMBNAILS);

    for (int i = 1; i <= NUMBER_OF_THUMBNAILS; i++) {

      final String output = String.format("%s/00%d.jpg", videoPath.getParent(), i);

      final double timestamp = (i - 0.5) * (duration / NUMBER_OF_THUMBNAILS) * 60;

      try {
        processService.execute(
            Commands.createThumbnail(
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
          processService.execute(Commands.calculateDuration(videoPath.toFile().getAbsolutePath()));

      return Double.parseDouble(output);

    } catch (final IOException | InterruptedException e) {
      log.error("failed to calculate duration {} {} {}", videoPath, e.getMessage(), e);

      return -1;
    }
  }
}
