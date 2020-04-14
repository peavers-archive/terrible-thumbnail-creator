/* Licensed under Apache-2.0 */
package io.terrible.thumbnail.creator.domain;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {

  private String videoPath;

  @Builder.Default private ArrayList<String> thumbnails = new ArrayList<>();
}
