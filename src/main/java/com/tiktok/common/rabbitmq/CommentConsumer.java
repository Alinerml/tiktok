package com.tiktok.common.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import com.tiktok.bean.Comment;
import com.tiktok.bean.Video;
import com.tiktok.bean.VideoComment;
import com.tiktok.bean.dto.CommentDto;
import com.tiktok.common.common.contants.enums.ExceptionEnum;
import com.tiktok.common.common.exception.TiktokException;
import com.tiktok.common.common.utils.DateUtils;
import com.tiktok.common.common.utils.JwtUtil;
import com.tiktok.common.common.utils.RedisUtil_db0;
import com.tiktok.common.springbootwebsocketdemo.util.JsonUtil;
import com.tiktok.service.ICommentService;
import com.tiktok.service.IVideoCommentService;
import com.tiktok.service.IVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CommentConsumer {
    private static final String QUEUE_NAME = "comment_queue";
    @Autowired
    ICommentService commentService;
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);


    @RabbitListener(queues = MQConfig.COMMENT_QUEUE)//当生产者将消息发送到队列时，消费者会自动接收到该消息receive方法会被调用
    public void startConsumingComments(String message) {
//        final AtomicReference<Comment> commentReference = new AtomicReference<>();
//
//        try {
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("127.0.0.1");
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//            LOGGER.info("Waiting for comment tasks...");
//
//            Consumer consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope,
//                                           AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
//                    String message = new String(body, "UTF-8");
//                    LOGGER.info("Received message from RabbitMQ: " + message);
//                    commentReference.set(processCommentTask(message));
//                }
//            };
//
//            channel.basicConsume(QUEUE_NAME, true, consumer);

        log.info("receive message:" + message);
        Comment comment = RedisUtil_db0.stringToBean(message, Comment.class);
        commentService.save(comment);
    }

//    private Comment processCommentTask(String message) {
//        // 反序列化字符串为 CommentDto 对象，可以使用 JSON、XML、Protobuf 等
//        // 这里以 JSON 为例
//        CommentDto commentDto = new Gson().fromJson(message, CommentDto.class);
//
//        // 调用 comment 方法进行评论操作
//        Comment comment = comment(commentDto);
//
//        return comment;
//    }
//
//    private Comment comment(CommentDto commentDto) {
//        //自动获取登录用户信息 -解析token获得...
//        String token = commentDto.getToken();
//        String userIdFromToken = JwtUtil.getUserIdFromToken(token);
//        if (!JwtUtil.validateToken(token)) {
//            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
//        }
//
//        try {
//            String videoId = commentDto.getVideo_id();
//            Integer actionType = commentDto.getAction_type();
//            String commentText = commentDto.getComment_text();
//            String commentId = commentDto.getComment_id();
//
//            //获取当前日期
//            String createTime = DateUtils.getCurrentDate();
//
//            Video video = videoService.getById(videoId);
//            //发布评论 -修改video中数量+新增comment表+新增videoCommentService+返回comment对象
//            if (actionType == 1) {
//                video.setCommentCount(video.getCommentCount() + 1);
//                videoService.updateById(video);
//
//                Comment comment = new Comment();
//                comment.setUserId(userIdFromToken);
//                comment.setContent(commentText);
//                comment.setCreateTime(createTime);
//                commentService.save(comment);
//
//                //中间表的新增
//                VideoComment videoComment = new VideoComment();
//                videoComment.setCommentId(commentId);
//                videoComment.setVideoId(videoId);
//                videoCommentService.save(videoComment);
//
//                return comment;
//            }
//
//            //删除评论 -删除comment表记录+修改video中数量+返回空
//            if (actionType == 2) {
//                commentService.removeById(commentId);
//                video.setCommentCount(video.getCommentCount() - 1);
//                videoService.updateById(video);
//                return null;
//            }
//        }
//
//        catch (Exception e) {
//            throw new TiktokException(e);
//        }
//        return null;
//    }
}
