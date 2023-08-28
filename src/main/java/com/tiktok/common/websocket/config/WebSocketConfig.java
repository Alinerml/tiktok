package com.tiktok.common.springbootwebsocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker //启用WebSocket消息代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {//WebSocketMessageBrokerConfigurer接口用于配置WebSocket消息代理

    /**
     * 用于注册STOMP协议的端点，并设置使用SockJS作为WebSocket的后备传输方式
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //WebSocket 连接将侦听路径为 "/ws" 的端点。用于在Spring应用程序中注册STOMP协议的端点，并设置使用SockJS作为WebSocket的后备传输方式。
        /*
        STOMP（Simple Text Oriented Messaging Protocol）是一种基于文本的、简单的消息传递协议，通常用于在Web应用程序中实现实时的双向通信。
        SockJS 是一个用于在不支持 WebSocket 的浏览器上提供类似 WebSocket 的实时双向通信的JavaScript库。它提供了一种向后兼容的方式，以确保在各种浏览器上能够实现实时通信。
         */
        registry.addEndpoint("/ws").withSockJS();
    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //以“/app”开头的消息应该路由到消息处理方法
        registry.setApplicationDestinationPrefixes("/app");

        //消息会被广播到匹配指定前缀的目标的所有订阅者
        registry.enableSimpleBroker("/topic");


        //   Use this for enabling a Full featured broker like RabbitMQ
        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }
}
