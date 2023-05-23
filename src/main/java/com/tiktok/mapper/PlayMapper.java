package com.tiktok.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiktok.bean.PlayInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface PlayMapper extends BaseMapper<PlayInfo> { //imp改ex
    /**
     * 视频投稿时 将用户设定的data和title绑定其他值
     * @return
     */
    Boolean InsertContribute(String data, String title, String play_url, String cover_url);

    /**
     * 依照id字段查询play全部数据
     * @param id
     * @return
     */
    PlayInfo SelectById(Integer id);
}
