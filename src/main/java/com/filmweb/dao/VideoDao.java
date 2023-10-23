package com.filmweb.dao;

import com.filmweb.entity.Video;

import java.util.List;

public interface VideoDao {
    Video findById(Integer id);

    Video findByHref(String href);

    List<Video> findAll();

    List<Video> findAllVideoDelete();

    List<Video> findByName(String name);

    List<Video> findAll(int pageNumber, int pageSize);

    List<Video> findAllVideoDelete(int pageNumber, int pageSize);

    List<Video> findVideoTrending();

    Video create(Video entity);

    Video update(Video entity);

    Video delete(Video entity);
}
