package com.moviex.service;

import static com.moviex.domain.video.VideoPaymentType.FREE;
import static com.moviex.domain.video.VideoPaymentType.PAID;

import com.moviex.dao.CategoryDao;
import com.moviex.dao.VideoDao;
import com.moviex.domain.video.payload.VideoCreationPayload;
import com.moviex.domain.video.payload.VideoUpdatePayload;
import com.moviex.dto.VideoDto;
import com.moviex.entity.Category;
import com.moviex.entity.Video;
import com.moviex.mapper.VideoMapper;
import com.moviex.utils.PriceFormatUtils;
import com.moviex.utils.RandomUtils;
import com.moviex.utils.SlugUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.security.SecureRandom;
import java.util.Date;
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
    
    public VideoDto createNewVideo(VideoCreationPayload payload) {
        var category = categoryDao.findById(payload.getCategoryId());
        
        var price = PriceFormatUtils.toNumber(payload.getFormattedPrice());
        
        var slug = generateUniqueSlug(payload.getTitle());
        
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
        
        var slug = generateUniqueSlug(payload.getTitle());
        
        video.setHref(payload.getHref());
        video.setPoster(buildVideoPosterLink(video.getHref()));
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

    // TODO: Use ID
    public VideoDto restore(String href) {
        Video video = videoDao.findByHref(href);
        // TODO: use deleted_at instead
        video.setIsActive(Boolean.TRUE);
        return videoMapper.toDto(videoDao.update(video));
    }
    
    // TODO: Use ID
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
    
    private String generateUniqueSlug(String title) {
        String slug = SlugUtils.generateSlug(title);
        
        while (videoDao.existingBySlug(slug)) {
            slug = SlugUtils.generateSlug(title, String.valueOf(new Date().getTime()));
        };
        
        return slug;
    }
    
    private String buildVideoPosterLink(String href) {
        return String.format(VIDEO_POSTER_PATTERN, href);
    }
}
