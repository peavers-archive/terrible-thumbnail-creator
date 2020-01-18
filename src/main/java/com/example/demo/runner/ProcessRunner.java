/* Licensed under Apache-2.0 */
package com.example.demo.runner;

import com.example.demo.service.FFmpegService;
import java.nio.file.Path;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@RequiredArgsConstructor
public class ProcessRunner implements CommandLineRunner {

  private final FFmpegService ffmpegService;

  private final Path videoPath;

  @Override
  public void run(final String... strings) {

    final ArrayList<Path> thumbnails = ffmpegService.createThumbnails(videoPath);

    log.info("Created {}", thumbnails.toString());
  }
}
