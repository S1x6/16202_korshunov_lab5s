package server;

import com.google.gson.Gson;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import model.Message;
import model.Model;
import model.User;
import model.json.MessageObject;
import model.json.NewMessageObject;
import route.HttpRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.websocket;

public class Server {

    private Undertow server;
    private Undertow websocketServer;
    private Timer timeoutCheckerTimer;
    private volatile Gson gson = new Gson();

    public Server() {
        HttpHandler asyncHandler = exchange -> (
                new HttpRouter()).getHandler(
                exchange.getRelativePath(),
                exchange.getRequestMethod()).performAction(exchange);

        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(asyncHandler)
                .build();
        websocketServer = Undertow.builder()
                .addHttpListener(8081, "localhost")
                .setHandler(path()
                        .addExactPath("/messages", websocket((exchange, channel) -> {
                            Model.setsMessageListener(msg -> {
                                String json = gson.toJson(msg);
                                for (WebSocketChannel session : channel.getPeerConnections()) {
                                    WebSockets.sendText(json, session, null);
                                }
                            });
                            channel.getReceiveSetter().set(new AbstractReceiveListener() {

                                @Override
                                protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                    final String messageData = message.getData();
                                    NewMessageObject messageObject = gson.fromJson(messageData, NewMessageObject.class);
                                    Message msg = Model.getInstance().addMessage(messageObject.getMessage(),
                                            -2, messageObject.getName());
                                    String json = gson.toJson(msg);
                                    for (WebSocketChannel session : channel.getPeerConnections()) {
                                        WebSockets.sendText(json, session, null);
                                    }
                                }
                            });
                            channel.resumeReceives();
                        }))).build();
        this.timeoutCheckerTimer = new Timer();
    }

    public void start() {
        server.start();
        websocketServer.start();
        timeoutCheckerTimer.schedule(new TimeoutCheckerThread(), 1000, 3000);
    }

    private class TimeoutCheckerThread extends TimerTask {

        @Override
        public void run() {
            List<User> users = new ArrayList<>(Model.getInstance().getUsers());
            for (User user : users) {
                if (user.shouldBeKicked()) {
                    Model.getInstance().removeUser(user);
                    Model.getInstance().addServerMessage(user.getName() + " was kicked by timeout");
                }
            }
        }
    }


}
