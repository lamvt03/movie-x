package com.filmweb.service;

import com.filmweb.entity.Video;

public interface VideoService {
    Video findByHref(String href);
}
