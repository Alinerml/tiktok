package com.tiktok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.PlayInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;

public interface IPlayService extends IService<PlayInfo> {
    /**
     * 视频投稿
     * @param data
     * @param token
     * @param title
     * @return
     */
    void contribute(String data, String token, String title);

    /**
     * 1 验证token
     * 2 返回playInfo和user全部数据
     * @param token
     * @param id
     * @return
     */
    HashMap<String, Object> distribute(String token, String id);
}
