package com.moviex.api.resp;

import com.moviex.dto.VideoDto;

import java.util.List;

public record VideoListResp(
        List<VideoDto> videos,
        int currentPage,
        int maxPage
){
}
