package noshow.Noshow_blue_2025.infra;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    public void requestInfoFromClients() {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage("{\"type\":\"request_info\"}"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}