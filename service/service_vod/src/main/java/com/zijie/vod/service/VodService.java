package com.zijie.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-21 20:19
 * @Description
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    boolean removeAliYunVideo(String id);

    boolean removeVideos(List<String> list);
}
