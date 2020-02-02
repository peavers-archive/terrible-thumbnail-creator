/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.configuration;

import com.beust.jcommander.JCommander;
import io.terrible.thumbnail.creator.Application;
import io.terrible.thumbnail.creator.service.ThumbnailService;
import java.nio.file.Paths;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TaskConfig {

  private final Application.Args args = new Application.Args();

  private final ThumbnailService thumbnailService;

  private final DataSource dataSource;

  @Bean
  public DataSourceConfig getTaskConfigurer() {
    return new DataSourceConfig(dataSource);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      JCommander.newBuilder().addObject(this.args).build().parse(args);

      thumbnailService.createThumbnails(Paths.get(this.args.getVideo()), this.args.getCount());
    };
  }
}
