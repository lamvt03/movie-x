package com.filmweb.service;

import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Video;

import java.util.List;

public interface VideoService {
    VideoDto findByHref(String href);
    VideoDto findBySlug(String slug);
    List<VideoDto> findTrending(int limit);
    List<VideoDto> findAll(int page, int limit);
    long countActiveVideos();
    List<VideoDto> findDeletedVideos(int page, int limit);
    long countDisabled();
    List<VideoDto> findByKeyword(String keyword);

    Video create(String title, String href, String poster, String director, String actor, String categoryCode, String description, String formattedPrice);

    VideoDto update(String title, String href, String director, String actor, String categoryCode, String heading, String formattedPrice, String description);

    VideoDto restore(String href);

    VideoDto delete(String href);

    List<VideoDto> findLikedVideos(int page, int limit);
    long countLikedVideos();
    List<VideoDto> findByCategoryCode(String code, int page, int limit);

    List<VideoDto> findTopYear(int year, int page, int limit);

    long countAllLikedVideos();

    List<VideoDto> findOtherVideos(String categoryCode, int page, int limit);
}
