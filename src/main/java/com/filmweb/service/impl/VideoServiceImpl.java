package com.filmweb.service.impl;

import com.filmweb.dao.CategoryDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Category;
import com.filmweb.entity.Video;
import com.filmweb.mapper.VideoMapper;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    @Inject
    private VideoDao videoDao;

    @Inject
    private CategoryDao categoryDao;

    @Inject
    private VideoMapper videoMapper;

    @Override
    public VideoDto findByHref(String href) {
        Video video = videoDao.findByHref(href);
        return videoMapper.toDto(video);
    }

    @Override
    public List<VideoDto> findTrending(int limit) {
        List<Video> trendingVideos = videoDao.findTrending(limit);
        return trendingVideos.stream()
                .map(videoMapper::toDto)
                .toList();
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
        return videoDao.count(true);
    }

    @Override
    public List<VideoDto> findAllDisabled(int page, int limit) {
        return videoDao.findAllDeletedVideos(page, limit).stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public long countDisabled() {
        return videoDao.count(false);
    }

    @Override
    public List<VideoDto> findByKeyword(String keyword) {
        List<Video> videos = videoDao.findByKeyword(keyword);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public VideoDto create(String title, String href, String poster, String director, String actor, String categoryCode, String description, String formattedPrice, String content) {
        Long price = Long.parseLong(formattedPrice.replace(".", ""));
        Category category = categoryDao.findByCode(categoryCode);
        Video video = videoDao.create(
                Video.builder()
                        .title(title)
                        .href(href)
                        .poster(poster)
                        .director(director)
                        .actor(actor)
                        .category(category)
                        .heading(description)
                        .price(price)
                        .description(content)
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .updatedAt(new Timestamp(System.currentTimeMillis()))
                        .isActive(Boolean.TRUE)
                        .views(0)
                        .share(0)
                        .build()
        );
        return videoMapper.toDto(video);
    }

    @Override
    public VideoDto update(String title, String href, String director, String actor, String categoryCode, String heading, String formattedPrice, String description) {
        Video video = videoDao.findByHref(href);
        Category category = categoryDao.findByCode(categoryCode);
        video.setTitle(title);
        video.setDirector(director);
        video.setActor(actor);
        video.setCategory(category);
        video.setHeading(heading);
        video.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Long price = Long.parseLong(formattedPrice.replace(".", ""));
        video.setPrice(price);
        video.setDescription(description);
        return videoMapper.toDto(videoDao.update(video));
    }

    @Override
    public VideoDto restore(String href) {
        Video video = videoDao.findByHref(href);
        video.setActive(Boolean.TRUE);
        return videoMapper.toDto(videoDao.update(video));
    }

    @Override
    public VideoDto delete(String href) {
        Video video = videoDao.findByHref(href);
        video.setActive(Boolean.FALSE);
        return videoMapper.toDto(
                videoDao.update(video)
        );
    }

    @Override
    public List<VideoDto> findAllLiked(int page, int limit) {
        List<VideoDto> videos = videoDao.findAll().stream()
                .map(videoMapper::toDto)
                .sorted(Comparator.comparingInt(VideoDto::getLikeQuantity).reversed())
                .toList();
        List<VideoDto> result = new ArrayList<>();
        int start = (page-1)*limit;
        int end = start + limit;
        for(int i = start; i <= end && i < videos.size(); i++ ){
            result.add(videos.get(i));
        }
        return result;
    }

    @Override
    public List<VideoDto> findByCategoryCode(String code) {
        List<Video> videos = videoDao.findByCategoryCode(code);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }
}
