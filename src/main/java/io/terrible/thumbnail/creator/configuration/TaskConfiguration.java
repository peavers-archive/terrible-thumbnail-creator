/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.configuration;

import com.beust.jcommander.JCommander;
import io.terrible.thumbnail.creator.Application;
import io.terrible.thumbnail.creator.service.ThumbnailService;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@EnableTask
@Configuration
@RequiredArgsConstructor
public class TaskConfiguration {

  private final Application.InputArgs inputArgs = new Application.InputArgs();

  private final ThumbnailService thumbnailService;

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      JCommander.newBuilder().addObject(inputArgs).build().parse(args);

      thumbnailService.createThumbnails(Paths.get(inputArgs.getVideo()), inputArgs.getCount());
    };
  }
}
