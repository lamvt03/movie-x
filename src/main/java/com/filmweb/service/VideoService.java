package com.filmweb.service;

import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Video;

import java.util.List;

public interface VideoService {
    VideoDto findByHref(String href);
    List<Video> findTrending(int limit);
    List<VideoDto> findAll(int page, int limit);
    long count();

    List<VideoDto> findByKeyword(String keyword);

    VideoDto create(String title, String href, String poster, String director, String actor, String category, String description, String formattedPrice, String content);

    VideoDto update(String title, String href, String director, String actor, String category, String heading, String formattedPrice, String description);
}
