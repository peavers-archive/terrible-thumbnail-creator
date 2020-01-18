/* Licensed under Apache-2.0 */
package com.example.demo;

import com.example.demo.runner.ProcessRunner;
import com.example.demo.service.FFmpegService;
import com.example.demo.service.FFmpegServiceImpl;
import com.example.demo.service.ProcessService;
import com.example.demo.service.ProcessServiceImpl;
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
    final FFmpegService fFmpegService = new FFmpegServiceImpl(processService);

    return new ProcessRunner(fFmpegService, Paths.get("TODO"));
  }
}
