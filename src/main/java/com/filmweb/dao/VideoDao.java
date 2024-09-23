package com.filmweb.dao;

import com.filmweb.entity.Video;

import java.util.List;

public interface VideoDao {

    Video findById(Long id);

    List<Video> findAll();

    List<Video> findAll(int page, int limit);

    List<Video> findByIsActiveFalseOrderByUpdatedAtDesc(int page, int limit);

    Video findByHref(String href);
    Video findBySlug(String slug);

    List<Video> findTrending(int limit);

    long count(boolean isActive);

    Video create(Video entity);

    Video update(Video entity);

    Video delete(Video entity);

    List<Video> findByKeyword(String keyword);
    List<Video> findByCategoryCodeAndViewsDesc(String categoryCode, int page, int limit);

    List<Video> findTopYear(int year, int page, int limit);
    List<Video> findLikedVideos(int page, int limit);
    long countLikedVideos();

    long countAllLikedVideos();
}
