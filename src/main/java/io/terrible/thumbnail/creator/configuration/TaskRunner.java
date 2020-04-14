/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.configuration;

import com.beust.jcommander.JCommander;
import io.terrible.thumbnail.creator.Application;
import io.terrible.thumbnail.creator.domain.Result;
import io.terrible.thumbnail.creator.service.MessageService;
import io.terrible.thumbnail.creator.service.ThumbnailService;
import java.nio.file.Paths;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRunner implements CommandLineRunner {

  private final Application.Args args = new Application.Args();

  private final ThumbnailService thumbnailService;

  private final MessageService messageService;

  @Override
  public void run(final String... strings) {

    JCommander.newBuilder().addObject(this.args).build().parse(strings);

    final String path = this.args.getVideo();
    final int count = this.args.getCount();

    final ArrayList<String> thumbnails = thumbnailService.createThumbnails(Paths.get(path), count);

    final Result result = Result.builder().videoPath(path).thumbnails(thumbnails).build();

    messageService.send(new GenericMessage<>(result));
  }
}
