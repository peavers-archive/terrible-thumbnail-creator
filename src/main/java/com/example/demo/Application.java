package com.example.demo;

import com.example.demo.service.FFmpegService;
import com.example.demo.service.FFmpegServiceImpl;
import com.example.demo.service.ProcessService;
import com.example.demo.service.ProcessServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public org.springframework.boot.CommandLineRunner commandLineRunner() {

    final ProcessService processService = new ProcessServiceImpl();
    final FFmpegService fFmpegService = new FFmpegServiceImpl(processService);

    return new ApplicationCommandLineRunner(fFmpegService, Paths.get("TODO"));
  }

  public static class ApplicationCommandLineRunner implements CommandLineRunner {

    private final FFmpegService ffmpegService;

    private final Path videoPath;

    public ApplicationCommandLineRunner(final FFmpegService ffmpegService, final Path videoPath) {
      this.ffmpegService = ffmpegService;
      this.videoPath = videoPath;
    }

    @Override
    public void run(final String... strings) {

      final ArrayList<Path> thumbnails = ffmpegService.createThumbnails(videoPath);

      System.out.println(thumbnails.toString());
    }
  }
}
