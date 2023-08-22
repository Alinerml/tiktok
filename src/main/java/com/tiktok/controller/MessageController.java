package com.tiktok.controller;

import com.tiktok.bean.Message;
import com.tiktok.bean.User;
import com.tiktok.bean.dto.MessageChatDto;
import com.tiktok.bean.dto.MessageDto;
import com.tiktok.common.api.vo.Result;
import com.tiktok.service.IMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * @Description: message
 * @Author: jeecg-boot
 * @Date:   2023-08-10
 * @Version: V1.0
 */
@Api(tags="message")
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {
	@Autowired
	private IMessageService messageService;

	 /**
	  * @description:  消息操作-发送消息
	  */
	 @ApiOperation(value="消息操作", notes="消息操作")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/action")
	 public Result<String> action(@RequestBody MessageDto messageDto) {
		 messageService.action(messageDto);
		 return Result.OK("添加成功！");
	 }

	 /**
	  * @description:  聊天记录-获取记录
	  */
	 @ApiOperation(value="聊天记录", notes="聊天记录")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/chat")
	 public Result chat(@RequestBody MessageChatDto messageChatDto) {
		 List<Message> messages = messageService.chat(messageChatDto);
		 return Result.OK(messages);
	 }

}
