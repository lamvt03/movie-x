package com.filmweb.service;

import static com.filmweb.domain.video.VideoPaymentType.FREE;
import static com.filmweb.domain.video.VideoPaymentType.PAID;

import com.filmweb.dao.CategoryDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.domain.video.VideoCreationPayload;
import com.filmweb.domain.video.VideoPaymentType;
import com.filmweb.domain.video.VideoUpdatePayload;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Category;
import com.filmweb.entity.Video;
import com.filmweb.mapper.VideoMapper;
import com.filmweb.utils.PriceFormatUtils;
import com.filmweb.utils.RandomUtils;
import com.filmweb.utils.SlugUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VideoService {
    
    private final static String VIDEO_POSTER_PATTERN = "https://img.youtube.com/vi/%s/maxresdefault.jpg";

    @Inject
    private VideoDao videoDao;

    @Inject
    private CategoryDao categoryDao;

    @Inject
    private VideoMapper videoMapper;

    @Inject
    private RandomUtils randomUtils;

    public VideoDto findByHref(String href) {
        Video video = videoDao.findByHref(href);
        return videoMapper.toDto(video);
    }
    
    public VideoDto findById(UUID id) {
        Video video = videoDao.findById(id);
        return videoMapper.toDto(video);
    }
    
    public VideoDto findBySlug(String slug) {
        Video video = videoDao.findBySlug(slug);
        return videoMapper.toDto(video);
    }

    public List<VideoDto> findTrending(int limit) {
        List<Video> trendingVideos = videoDao.findTrending(limit);
        return trendingVideos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    public List<VideoDto> findAll(int page, int limit) {
        List<Video> videos = videoDao.findAll(page, limit);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    public long countActiveVideos() {
        return videoDao.count(true);
    }

    public List<VideoDto> findDeletedVideos(int page, int limit) {
        return videoDao.findByIsActiveFalseOrderByUpdatedAtDesc(page, limit).stream()
                .map(videoMapper::toDto)
                .toList();
    }

    public long countDisabled() {
        return videoDao.count(false);
    }

    public List<VideoDto> findByKeyword(String keyword) {
        List<Video> videos = videoDao.findByKeyword(keyword);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    //TODO : delete
    public Video create(String title, String href, String poster, String director, String actor, String categorySlug, String description, String formattedPrice) {
        long price = Long.parseLong(formattedPrice.replace(".", ""));
        Category category = categoryDao.findBySlug(categorySlug);
        
        // TODO: generate slug

        // TODO: fix this
        
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
                        .paymentType(price > 0 ? PAID : FREE)
                        .description(description)
                        .isActive(Boolean.TRUE)
                        .views(random.nextInt(15))
                        .share(0)
                        .build()
        );
    }
    
    public VideoDto createNewVideo(VideoCreationPayload payload) {
        var category = categoryDao.findById(payload.getCategoryId());
        
        var price = PriceFormatUtils.toNumber(payload.getFormattedPrice());
        
        // TODO: make sure slug always unique
        var slug = SlugUtils.generateSlug(payload.getTitle());
        
        SecureRandom random = new SecureRandom();
        
        var videoCreated = videoDao.create(
            Video.builder()
                .title(payload.getTitle())
                .slug(slug)
                .href(payload.getHref())
                .poster(buildVideoPosterLink(payload.getHref()))
                .director(payload.getDirector())
                .actor(payload.getActor())
                .category(category)
                .price(price)
                .paymentType(price > 0 ? PAID : FREE)
                .description(payload.getDescription())
                .isActive(Boolean.TRUE)
                .views(random.nextInt(15))
                .share(0)
                .build()
        );
        
        return videoMapper.toDto(videoCreated);
    }
    
    public VideoDto updateVideo(VideoUpdatePayload payload) {
        Video video = videoDao.findById(payload.getId());
        Category category = categoryDao.findBySlug(payload.getCategorySlug());
        long price = PriceFormatUtils.toNumber(payload.getFormattedPrice());
        
        // TODO: make sure slug always unique
        var slug = SlugUtils.generateSlug(payload.getTitle());
        
        video.setTitle(payload.getTitle());
        video.setDirector(payload.getDirector());
        video.setActor(payload.getActor());
        video.setCategory(category);
        video.setPrice(price);
        video.setSlug(slug);
        video.setPaymentType(price > 0 ? PAID : FREE);
        video.setDescription(payload.getDescription());
        
        return videoMapper.toDto(videoDao.update(video));
    }

    // TODO: delete
    public VideoDto update(String title, String href, String director, String actor, String categoryCode, String heading, String formattedPrice, String description) {
        Video video = videoDao.findByHref(href);
        Category category = categoryDao.findBySlug(categoryCode);
        video.setTitle(title);
        video.setDirector(director);
        video.setActor(actor);
        video.setCategory(category);
        
        long price = Long.parseLong(formattedPrice.replace(".", ""));
        video.setPrice(price);
        video.setPaymentType(price > 0 ? PAID : FREE);
        video.setDescription(description);
        return videoMapper.toDto(videoDao.update(video));
    }

    public VideoDto restore(String href) {
        Video video = videoDao.findByHref(href);
        // TODO: use deleted_at instead
        video.setIsActive(Boolean.TRUE);
        return videoMapper.toDto(videoDao.update(video));
    }

    public VideoDto delete(String href) {
        Video video = videoDao.findByHref(href);
        video.setIsActive(Boolean.FALSE);
        return videoMapper.toDto(
                videoDao.update(video)
        );
    }

    public List<VideoDto> findLikedVideos(int page, int limit) {
        return videoDao.findLikedVideos(page, limit)
                .stream()
                .map(videoMapper::toDto)
                .toList();
    }

    public long countLikedVideos() {
        return videoDao.countLikedVideos();
    }

    public List<VideoDto> findByCategorySlug(String slug, int page, int limit) {
        List<Video> videos = videoDao.findByCategorySlugAndViewsDesc(slug, page, limit);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    public List<VideoDto> findTopYear(int year, int page, int limit) {
        List<Video> videos = videoDao.findTopYear(year, page, limit);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    public long countAllLikedVideos(){
        return videoDao.countAllLikedVideos();
    }

    public List<VideoDto> findOtherVideos(String slug, int page, int limit) {
        Category category = categoryDao.findBySlug(slug);
        long randomOrdinal = randomUtils.randomInRangeExcept(categoryDao.count(), category.getOrdinal());
        Category otherCategory = categoryDao.findByOrdinal(randomOrdinal);
        return findByCategorySlug(otherCategory.getSlug(), page, limit);
    }
    
    private String buildVideoPosterLink(String href) {
        return String.format(VIDEO_POSTER_PATTERN, href);
    }
}
