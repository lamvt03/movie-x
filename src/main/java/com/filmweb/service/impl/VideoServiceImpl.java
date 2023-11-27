package com.filmweb.service.impl;

import com.filmweb.dao.VideoDao;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Video;
import com.filmweb.mapper.VideoMapper;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    @Inject
    VideoDao videoDao;


    @Inject
    VideoMapper videoMapper;

    @Override
    public VideoDto findByHref(String href) {
        Video video = videoDao.findByHref(href);
        return videoMapper.toDto(video);
    }

    @Override
    public List<Video> findTrending(int limit) {
        return videoDao.findTrending(limit);
    }

    @Override
    public List<VideoDto> findAll(int page, int limit) {
        List<Video> videos = videoDao.findAll(page, limit);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public long count() {
        return videoDao.count();
    }

    @Override
    public List<VideoDto> findByKeyword(String keyword) {
        List<Video> videos = videoDao.findByKeyword(keyword);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }
}
