package com.filmweb.service.impl;

import com.filmweb.dao.VideoDao;
import com.filmweb.entity.Video;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    @Inject
    VideoDao videoDao;

    @Override
    public Video findByHref(String href) {
        return videoDao.findByHref(href);
    }
}
