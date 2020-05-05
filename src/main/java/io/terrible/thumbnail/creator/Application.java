/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator;

import com.beust.jcommander.Parameter;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@SpringBootApplication
public class Application {

  public static void main(final String[] args) {

    SpringApplication.run(Application.class, args);
  }

  @Data
  public static class Args {

    @Parameter(
        names = {"--input", "-i"},
        arity = 1,
        description = "Absolute path to video",
        required = true)
    private String input;

    @Parameter(
        names = {"--output", "-o"},
        arity = 1,
        description = "Absolute path to thumbnail output",
        required = true)
    private String output;

    @Parameter(
        names = {"--count", "-c"},
        arity = 1,
        description = "Number of thumbnails to create")
    private int count = 12;
  }
}
