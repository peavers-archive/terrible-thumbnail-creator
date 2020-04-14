/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.utils;

import java.util.ArrayList;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandUtil {

  public static ArrayList<String> createThumbnail(
      final String timestamp, final String videoPath, final String output) {
    final ArrayList<String> commands = new ArrayList<>();

    commands.add("ffmpeg");
    commands.add("-ss");
    commands.add(timestamp);
    commands.add("-i");
    commands.add(videoPath);
    commands.add("-vframes");
    commands.add("1");
    commands.add(output);

    return commands;
  }

  public static ArrayList<String> calculateDuration(final String videoPath) {
    final ArrayList<String> commands = new ArrayList<>();

    commands.add("ffprobe");
    commands.add("-show_entries");
    commands.add("format=duration");
    commands.add("-of");
    commands.add("default=noprint_wrappers=1:nokey=1");
    commands.add(videoPath);

    return commands;
  }
}
