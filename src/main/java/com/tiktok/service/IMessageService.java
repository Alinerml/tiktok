package com.tiktok.service;

import com.tiktok.bean.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.dto.MessageChatDto;
import com.tiktok.bean.dto.MessageDto;

import java.util.List;

/**
 * @Description: message
 * @Author: jeecg-boot
 * @Date:   2023-08-10
 * @Version: V1.0
 */
public interface IMessageService extends IService<Message> {

    void action(MessageDto messageDto);

    List<Message> chat(MessageChatDto messageChatDto);
}
