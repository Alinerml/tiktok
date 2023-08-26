package com.tiktok.common.springbootwebsocketdemo.listener;

import com.tiktok.common.springbootwebsocketdemo.model.ChatMessage;
import com.tiktok.common.springbootwebsocketdemo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * 添加WebSocket事件监听
 */
@Component
public class WebSocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Value("${server.port}")
    private String serverPort;

    @Value("${redis.set.onlineUsers}")
    private String onlineUsers;

    @Value("${redis.channel.userStatus}")
    private String userStatus;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 监听连接
     * (已经在ChatController中定义的addUser（）方法中广播了用户加入事件。因此，我们不需要在SessionConnected事件中执行任何操作。)
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        InetAddress localHost;
        try {
            localHost = Inet4Address.getLocalHost();
            LOGGER.info("Received a new web socket connection from:" + localHost.getHostAddress() + ":" + serverPort);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    /**
     * 监听断连
     * @param event
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        //这一行代码使用StompHeaderAccessor类包装事件中的消息，以便访问和处理该消息的头部信息
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //从WebSocket会话的属性中获取用户名，这个用户名是在用户加入聊天时存储的，在前面的addUser()方法中设置。
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            LOGGER.info("User Disconnected : " + username);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            try {
                redisTemplate.opsForSet().remove(onlineUsers, username);
                //使用messagingTemplate将消息发送到"/topic/public"目标，从而广播给所有订阅了该目标的客户端
                redisTemplate.convertAndSend(userStatus, JsonUtil.parseObjToJson(chatMessage));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }
}
