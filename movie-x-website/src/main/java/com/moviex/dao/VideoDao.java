package com.moviex.dao;

import com.moviex.entity.Video;

import java.util.List;
import java.util.UUID;

public interface VideoDao {

    Video findById(Long id);
    Video findById(UUID id);

    List<Video> findAll();

    List<Video> findAll(int page, int limit);

    List<Video> findByIsActiveFalseOrderByUpdatedAtDesc(int page, int limit);

    Video findByHref(String href);
    Video findBySlug(String slug);
    boolean existingBySlug(String slug);

    List<Video> findTrending(int limit);

    long count(boolean isActive);

    Video create(Video entity);

    Video update(Video entity);

    Video delete(Video entity);

    List<Video> findByKeyword(String keyword);
    List<Video> findByCategorySlugAndViewsDesc(String categorySlug, int page, int limit);

    List<Video> findTopYear(int year, int page, int limit);
    List<Video> findLikedVideos(int page, int limit);
    long countLikedVideos();

    long countAllLikedVideos();
}
