/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator;

import com.beust.jcommander.Parameter;
import io.terrible.thumbnail.creator.configuration.TaskConfiguration;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /** Input arguments are passed into {@link TaskConfiguration} for validation and processing. */
  @Getter
  public static class InputArgs {

    @Parameter(
        names = {"--video", "-v"},
        description = "Absolute path to video",
        required = true)
    private String video;

    @Parameter(
        names = {"--count", "-c"},
        description = "Number of thumbnails to create")
    private int count = 12;
  }
}
