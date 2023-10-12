package edu.scnu.wiki.service.impl;

import edu.scnu.wiki.websocket.WebSocketService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author long
 * @version 1.0
 * @ClassName WsService
 * @description: TODO
 * @date 2023/10/12 20:03
 */
@Service
public class WsService {


    @Autowired
    private WebSocketService webSocketService;


    @Async
    public void sentInfo(String info, String logId){
        MDC.put("LOG_ID", logId);

        webSocketService.sendInfo(info);
    }

}
