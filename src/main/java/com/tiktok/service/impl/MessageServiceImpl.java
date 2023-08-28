package com.tiktok.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.FriendUser;
import com.tiktok.bean.Message;
import com.tiktok.bean.dto.MessageChatDto;
import com.tiktok.bean.dto.MessageDto;
import com.tiktok.common.common.contants.enums.ExceptionEnum;
import com.tiktok.common.common.exception.TiktokException;
import com.tiktok.common.common.utils.DateUtils;
import com.tiktok.common.common.utils.JwtUtil;
import com.tiktok.mapper.MessageMapper;
import com.tiktok.service.IFriendUserService;
import com.tiktok.service.IMessageService;
import com.tiktok.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Description: message
 * @Author: jeecg-boot
 * @Date:   2023-08-10
 * @Version: V1.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    IUserService userService;
    @Autowired
    IFriendUserService friendUserService;

    @Override
    public void action(MessageDto messageDto) {
        String token = messageDto.getToken(); //鉴权
        String toUserId = messageDto.getTo_user_id();//对方用户id
        Integer actionType = messageDto.getAction_type(); //*去改FriendUser中msgType
        String content = messageDto.getContent();//消息内容

        String userIdFromToken = JwtUtil.getUserIdFromToken(token);
        //先鉴权
        if (JwtUtil.validateToken(token)) {
            //去保存Message
            Message message = new Message();
            message.setToUserId(toUserId)
                    .setFromUserId(userIdFromToken)
                    .setContent(content)
                    .setCreateTime(DateUtils.getCurrentTimeString());

            this.save(message);

            //发消息后更新 FriendUser 中对应数据
            FriendUser friendUser = friendUserService.getById(toUserId);
            friendUser.setMsgType(actionType)
                    .setMessage(content);
            friendUserService.updateById(friendUser);
        }else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
    }

    @Override
    public List<Message> chat(MessageChatDto messageChatDto) {
        String token = messageChatDto.getToken();
        String toUserId = messageChatDto.getTo_user_id();
        String preMsgTime = messageChatDto.getPre_msg_time();

        String userIdFromToken = JwtUtil.getUserIdFromToken(token);

        if (!JwtUtil.validateToken(token)) {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }

        MPJLambdaWrapper<Message> wrapper = new MPJLambdaWrapper<Message>()
                .selectAll(Message.class)
                .eq(Message::getToUserId,toUserId)
                .eq(Message::getFromUserId,userIdFromToken)
                .ge(Message::getCreateTime,preMsgTime);  //大于等于preMsgTime

        List<Message> messages = messageMapper.selectJoinList(Message.class, wrapper);

        return messages;
    }
}
