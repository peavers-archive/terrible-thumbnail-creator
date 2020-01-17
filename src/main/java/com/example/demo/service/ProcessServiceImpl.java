/* Licensed under Apache-2.0 */
package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

  @Override
  public String execute(final List<String> command) throws IOException {

    log.info("command {}", Arrays.toString(command.toArray()).replace(",", ""));

    final ProcessBuilder processBuilder = new ProcessBuilder(command);
    final Process process = processBuilder.start();
    final String output = readConsole(process.getInputStream());
    final String error = readConsole(process.getErrorStream());

    logResult(output, error);

    process.getInputStream().close();
    process.getErrorStream().close();

    return output;
  }

  /**
   * Simple helper method to output the results of the process builder to the console.
   *
   * @param output input stream from process builder
   * @param error error stream from process builder
   */
  private void logResult(final String output, final String error) {

    if (StringUtils.isNoneEmpty(error)) {
      log.info(error);
    } else {
      log.info("result {}", output);
    }
  }

  /**
   * Collect the result from the console and report it back.
   *
   * @param stream input stream from the process builder
   * @return collection of all error messages
   */
  private String readConsole(final InputStream stream) {

    return new BufferedReader(new InputStreamReader(stream))
        .lines()
        .map(line -> line + System.getProperty("line.separator"))
        .collect(Collectors.joining());
  }
}
