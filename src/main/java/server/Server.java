package server;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import model.Model;
import model.User;
import route.HttpRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    private Undertow server;
    private Timer timeoutCheckerTimer;

    public Server() {
        HttpHandler asyncHandler = exchange -> (
                new HttpRouter()).getHandler(
                exchange.getRelativePath(),
                exchange.getRequestMethod()).performAction(exchange);

        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(asyncHandler).build();
        this.timeoutCheckerTimer = new Timer();
    }

    public void start() {
        server.start();
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
