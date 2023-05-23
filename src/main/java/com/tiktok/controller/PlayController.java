package com.tiktok.controller;

import cn.hutool.jwt.JWTUtil;
import com.tiktok.common.BaseResult;
import com.tiktok.service.IPlayService;
import com.tiktok.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/publish")
public class PlayController {
    @Autowired
    IPlayService iPlayService;

    private static final String UPLOAD_DIR = "C:\\file1\\";

    /**
     * 投稿接口
     * @param data
     * @param token
     * @param title
     * @return
     */
    @PostMapping("/action/")
    public BaseResult contribute(@RequestParam MultipartFile data,@RequestParam String token,@RequestParam String title){
        /*注意：
        内部逻辑实现都放service (可以存在mapper方法)
        数据库操作都放mapper
        数据获取与封装接口才放在controller
         */
        try {
            String filePath = UPLOAD_DIR + data.getOriginalFilename();
            Files.write(Paths.get(filePath), data.getBytes());
            iPlayService.contribute(filePath, token, title); //写入data的存储的路径 而不是一整个文件
            return BaseResult.ok();
        } catch (Exception e) {
            return BaseResult.error(403,e.getMessage());
        }
    }

    @GetMapping("/list/")
    public BaseResult distribute(@RequestParam String token,@RequestParam String user_id) {
        //先将逻辑写在最外层 再进行封装
        /*
        1 验证token
        2 返回playInfo和user全部数据
        注意：
        user_id为string 要自己转为int

        疑问：
        依照什么字段去查询play中数据？
         */
        HashMap<String, Object> video_list = iPlayService.distribute(token, user_id);
        try {
            return BaseResult.ok().put("video_list",video_list);
        } catch (Exception e) {
            return BaseResult.error(403,e.getMessage());
        }
    }
}
