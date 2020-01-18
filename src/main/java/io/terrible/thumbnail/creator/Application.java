/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator;

import io.terrible.thumbnail.creator.runner.ProcessRunner;
import io.terrible.thumbnail.creator.service.ProcessService;
import io.terrible.thumbnail.creator.service.ProcessServiceImpl;
import io.terrible.thumbnail.creator.service.ThumbnailService;
import io.terrible.thumbnail.creator.service.ThumbnailServiceImpl;
import java.nio.file.Paths;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    final ProcessService processService = new ProcessServiceImpl();
    final ThumbnailService thumbnailService = new ThumbnailServiceImpl(processService);

    return new ProcessRunner(thumbnailService, Paths.get("TODO"));
  }
}
