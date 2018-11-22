package com.core.redis;

import com.base.util.WebSocketUtil;
import com.core.websocket.LoginHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * redis监听消息
 */
@Component
public class RedisMessageListener implements MessageListener {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static Logger logger = Logger.getLogger(RedisMessageListener.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //获取消息
        byte[] body = message.getBody();
        // 使用ValueSerializer转换
        String msgContent = (String) redisTemplate.getValueSerializer().deserialize(body);
        //获取channel
        byte[] channel = message.getChannel();
        // key必须为stringSerializer,和redisTemplate.convertAndSend对应
        String topic = redisTemplate.getStringSerializer().deserialize(channel);
        if(topic.matches("(.*)Talk$")){//以talk结尾的通道名称,通过websocket发送消息
            Map<String,String> messageMap=new HashMap<String,String>();
            messageMap.put("msgType", "essayPublish");
            messageMap.put("essayMessage",msgContent);
            try {
                WebSocketUtil.sendMessageToAllUser(LoginHandler.getUserMap(),messageMap);
            } catch (Exception e) {
                logger.error("通过websocket给在线人员发送订阅消息失败.",e);
            }
        }
    }
}