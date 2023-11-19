package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.entity.Video;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class VideoDaoImpl extends AbstractDao<Video> implements VideoDao {

    @Override
    public Video findById(Long id) {
        return super.findById(Video.class, id);
    }

    @Override
    public List<Video> findAll() {
        return super.findAll(Video.class);
    }

    @Override
    public List<Video> findAll(int page, int limit) {
        return super.findAll(Video.class, page, limit);
    }

    @Override
    public List<Video> findAllDeletedVideos() {
        return super.findAll(Video.class, false);
    }

    @Override
    public List<Video> findAllDeletedVideos(int page, int limit) {
        return super.findAll(Video.class, false, page, limit);
    }

    @Override
    public Video findByHref(String href) {
        String jpql = "SELECT o FROM Video o WHERE o.href = ?1";
        return super.findOne(Video.class, jpql, href);
    }

    @Override
    public List<Video> findByName(String name) {
        String jpql = "SELECT o FROM Video o WHERE o.name = ?0";
        return super.findMany(Video.class, jpql, name);
    }

    @Override
    public List<Video> findVideoTrending() {
        return null;
    }
}
