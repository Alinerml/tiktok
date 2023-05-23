package com.tiktok.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.soap.Text;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("userfile")

public class UserFile {
    @TableId(type =  IdType.AUTO)
    private Integer id;

    private MultipartFile avatar; //用户头像

    private MultipartFile background_image; //用户个人页顶部大图

    private Text signature; //个人简介
}
