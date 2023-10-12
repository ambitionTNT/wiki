package edu.scnu.wiki.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author long
 * @version 1.0
 * @ClassName WebSocket
 * @description: TODO
 * @date 2023/10/12 10:25
 */
//注册成组件
@Component
//定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint("/websocket/{token}")
//如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;可以直接调用log.info
@Slf4j
public class WebSocketService {


    /**
     * 每个客户端一个token
     */
    private String token = "";

    private static HashMap<String, Session>  map = new HashMap<>();

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        map.put(token, session);
        log.info("有新连接：token：{}，session id：{}，当前连接数：{}", token, session.getId(), map.size());
    }

    //前端关闭时一个websocket时
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(@PathParam("token") String token) {
        map.remove(token);
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }
    /**
     * 连接错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
    }


    /**
     * 群发消息
     */
    public void sendInfo(String message) {
        for (String token : map.keySet()) {
            Session session = map.get(token);
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("推送消息失败：{}，内容：{}", token, message);
            }
            log.info("推送消息：{}，内容：{}", token, message);
        }
    }
}