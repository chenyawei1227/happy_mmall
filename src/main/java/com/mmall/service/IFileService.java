package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by chenyawei on 2017/7/18.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
