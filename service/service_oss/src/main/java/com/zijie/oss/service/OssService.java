package com.zijie.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author DZJ
 * @create 2021-10-15 15:51
 * @Description
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
