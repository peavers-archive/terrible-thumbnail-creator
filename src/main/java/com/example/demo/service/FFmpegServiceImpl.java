package com.example.demo.service;

import com.example.demo.utils.Commands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class FFmpegServiceImpl implements FFmpegService {

  private final ProcessService processService;

  @Override
  public ArrayList<Path> createThumbnails(final Path videoPath) {
    final double duration = calculateDuration(videoPath) / 60;

    final ArrayList<Path> thumbnails = new ArrayList<>(12);

    for (int i = 1; i <= 12; i++) {

      final String output = String.format("%s/00%d.jpg", videoPath.getParent(), i);

      final double timestamp = (i - 0.5) * (duration / 12) * 60;

      try {
        processService.execute(
            Commands.createThumbnail(
                String.valueOf(timestamp), videoPath.toFile().getAbsolutePath(), output));

        thumbnails.add(Paths.get(output));
      } catch (final IOException | InterruptedException e) {
        log.error("failed to create {} {} {}", videoPath, e.getMessage(), e);
      }
    }

    return thumbnails;
  }

  private double calculateDuration(final Path videoPath) {

    try {
      final String output =
          processService.execute(Commands.calculateDuration(videoPath.toFile().getAbsolutePath()));

      return Double.parseDouble(output);

    } catch (final IOException | InterruptedException e) {
      log.error("unable to calculate video duration {}", e.getMessage(), e);

      return -1;
    }
  }
}
