package com.filmweb.service;

import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Video;

import java.util.List;

public interface VideoService {
    VideoDto findByHref(String href);
    List<VideoDto> findTrending(int limit);
    List<VideoDto> findAll(int page, int limit);
    long count();
    List<VideoDto> findAllDisabled(int page, int limit);
    long countDisabled();
    List<VideoDto> findByKeyword(String keyword);

    VideoDto create(String title, String href, String poster, String director, String actor, String categoryCode, String description, String formattedPrice, String content);

    VideoDto update(String title, String href, String director, String actor, String categoryCode, String heading, String formattedPrice, String description);

    VideoDto restore(String href);

    VideoDto delete(String href);

    List<VideoDto> findAllLiked(int page, int limit);
    List<VideoDto> findByCategoryCode(String code);
}
