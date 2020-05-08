/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.configuration;

import com.beust.jcommander.JCommander;
import io.terrible.thumbnail.creator.Application;
import io.terrible.thumbnail.creator.domain.Result;
import io.terrible.thumbnail.creator.service.MessageService;
import io.terrible.thumbnail.creator.service.ThumbnailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskRunner implements CommandLineRunner {

  private final Application.Args args = new Application.Args();

  private final ThumbnailService thumbnailService;

  private final MessageService messageService;

  @Override
  public void run(final String... strings) {

    JCommander.newBuilder().addObject(this.args).build().parse(strings);

    final String input = this.args.getInput();
    final String output = this.args.getOutput();
    final int count = this.args.getCount();

    final ArrayList<String> thumbnails =
        thumbnailService.createThumbnails(Path.of(input), Path.of(output), count);

    final Result result = Result.builder().videoPath(input).thumbnails(thumbnails).build();

    log.info("Result {}", result);

    messageService.send(new GenericMessage<>(result));
  }

  @AfterTask
  public void afterMe(TaskExecution taskExecution) {

    taskExecution.setExitMessage("Completed");
  }
}
