package org.linitly.boot.base.helper.tool;

import lombok.extern.slf4j.Slf4j;
import org.linitly.boot.base.config.MyEndpointConfigure;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: linxiunan
 * @date: 2020/12/3 16:12
 * @descrption:
 */
@Slf4j
@Component
@ServerEndpoint(value = "/socket/{systemCode}/{userId}", configurator = MyEndpointConfigure.class)
public class WebSocket {

    private static AtomicInteger onlineCount = new AtomicInteger();

    private static ConcurrentHashMap<Integer, Map<Long, WebSocket>> sockets = new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(@PathParam("systemCode") Integer systemCode, @PathParam("userId") Long userId, Session session) {
        log.info("新客户端连入：系统码：{}，用户id：{}", systemCode, userId);
        this.session = session;
        Map<Long, WebSocket> webSocketMap = new ConcurrentHashMap<>();
        webSocketMap.put(userId, this);
        sockets.put(systemCode, webSocketMap);
        addOnlineCount();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("systemCode") Integer systemCode, @PathParam("userId") Long userId) {
        log.info("客户端关闭连接：系统码：{}，用户id：{}", systemCode, userId);
        sockets.get(systemCode).remove(userId);
        subOnlineCount();
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        // 哪个客户端发送回复给哪个客户端
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("webSocket出现错误");
        error.printStackTrace();
    }

    public static int getOnlineCount() {
        return onlineCount.intValue();
    }

    public static void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineCount.decrementAndGet();
    }

    public Session getSession() {
        return session;
    }
}
