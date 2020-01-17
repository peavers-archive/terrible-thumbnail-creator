package com.example.demo.service;

import java.nio.file.Path;
import java.util.ArrayList;

/** @author Chris Turner (chris@forloop.space) */
public interface FFmpegService {

  ArrayList<Path> createThumbnails(Path videoPath);
}
