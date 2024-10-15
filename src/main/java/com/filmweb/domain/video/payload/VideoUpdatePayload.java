package com.filmweb.domain.video.payload;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VideoUpdatePayload {
  UUID id;
  String title;
  String href;
  String director;
  String actor;
  String description;
  String formattedPrice;
  String categorySlug;
}
