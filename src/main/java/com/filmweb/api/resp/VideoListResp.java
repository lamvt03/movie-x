package com.filmweb.api.resp;

import com.filmweb.dto.VideoDto;

import java.util.List;

public record VideoListResp(
        List<VideoDto> videos,
        int currentPage,
        int maxPage
){
}
