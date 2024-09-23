package com.filmweb.service.impl;

import com.filmweb.dao.CategoryDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Category;
import com.filmweb.entity.Video;
import com.filmweb.mapper.VideoMapper;
import com.filmweb.service.VideoService;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.SecureRandom;
import java.util.List;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    @Inject
    private VideoDao videoDao;

    @Inject
    private CategoryDao categoryDao;

    @Inject
    private VideoMapper videoMapper;

    @Inject
    private RandomUtils randomUtils;

    @Override
    public VideoDto findByHref(String href) {
        Video video = videoDao.findByHref(href);
        return videoMapper.toDto(video);
    }
    
    @Override
    public VideoDto findBySlug(String slug) {
        Video video = videoDao.findBySlug(slug);
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
    public long countActiveVideos() {
        return videoDao.count(true);
    }

    @Override
    public List<VideoDto> findDeletedVideos(int page, int limit) {
        return videoDao.findByIsActiveFalseOrderByUpdatedAtDesc(page, limit).stream()
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
    public Video create(String title, String href, String poster, String director, String actor, String categoryCode, String description, String formattedPrice) {
        Long price = Long.parseLong(formattedPrice.replace(".", ""));
        Category category = categoryDao.findByCode(categoryCode);
        SecureRandom random = new SecureRandom();
        return videoDao.create(
                Video.builder()
                        .title(title)
                        .href(href)
                        .poster(poster)
                        .director(director)
                        .actor(actor)
                        .category(category)
                        .price(price)
                        .description(description)
                        .isActive(Boolean.TRUE)
                        .views(random.nextInt(15))
                        .share(0)
                        .build()
        );
    }

    @Override
    public VideoDto update(String title, String href, String director, String actor, String categoryCode, String heading, String formattedPrice, String description) {
        Video video = videoDao.findByHref(href);
        Category category = categoryDao.findByCode(categoryCode);
        video.setTitle(title);
        video.setDirector(director);
        video.setActor(actor);
        video.setCategory(category);
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
    public List<VideoDto> findLikedVideos(int page, int limit) {
        return videoDao.findLikedVideos(page, limit)
                .stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public long countLikedVideos() {
        return videoDao.countLikedVideos();
    }

    @Override
    public List<VideoDto> findByCategoryCode(String code, int page, int limit) {
        List<Video> videos = videoDao.findByCategoryCodeAndViewsDesc(code, page, limit);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public List<VideoDto> findTopYear(int year, int page, int limit) {
        List<Video> videos = videoDao.findTopYear(year, page, limit);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public long countAllLikedVideos(){
        return videoDao.countAllLikedVideos();
    }

    @Override
    public List<VideoDto> findOtherVideos(String categoryCode, int page, int limit) {
        Category category = categoryDao.findByCode(categoryCode);
        long randomId = randomUtils.randomInRangeExcept(categoryDao.count(), category.getId());
        Category otherCategory = categoryDao.findById(randomId);
        return findByCategoryCode(otherCategory.getCode(), page, limit);
    }
}
