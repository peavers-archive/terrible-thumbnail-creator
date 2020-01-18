# Terrible Thumbnail Creator

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5f5c265f16e849e7ad3501a120610df2)](https://app.codacy.com/manual/peavers/terrible-thumbnail-creator?utm_source=github.com&utm_medium=referral&utm_content=peavers/terrible-thumbnail-creator&utm_campaign=Badge_Grade_Dashboard)

Small Spring Task which wraps over FFMPEG to generate a set number of thumbnails at even intervals dependant on the length of the video input.

## Usage

### Arguments
* `--video` The absolute path to to the video file.
* `--count` The number of thumbnails to create, the more, the longer the task will execute. Default is 12.

#### Example
```shell script
java -jar terrible-thumbnail-creator-jar --video input.mp4 --count 6
```
