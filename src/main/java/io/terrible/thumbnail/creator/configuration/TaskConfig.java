/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.configuration;

import io.terrible.thumbnail.creator.service.MessageService;
import io.terrible.thumbnail.creator.service.ThumbnailService;
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

  private final ThumbnailService thumbnailService;

  private final MessageService messageService;

  private final DataSource dataSource;

  @Bean
  public DataSourceConfig getTaskConfigurer() {

    return new DataSourceConfig(dataSource);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return new TaskRunner(thumbnailService, messageService);
  }
}
