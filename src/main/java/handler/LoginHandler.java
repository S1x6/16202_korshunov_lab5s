package handler;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import model.Model;
import model.User;
import model.json.AuthObject;

public class LoginHandler extends RequestHandler {
    @Override
    public void performAction(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((ex, data) -> {
            AuthObject authObject = gson.fromJson(new String(data), AuthObject.class);
            if (authObject != null && authObject.getUsername() != null) {

                String username = authObject.getUsername();
                if (Model.getInstance().getUsers().stream().anyMatch(user -> user.getName().equals(username))) {
                    exchange.getResponseHeaders().add(new HttpString("WWW-Authenticate"),
                            "Token realm='Username is already in use'");
                    exchange.setStatusCode(401);
                    exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
                    exchange.getResponseSender().send("");
                } else {
                    User user = Model.getInstance().addUser(username);
                    exchange.getResponseSender().send(gson.toJson(user.getUserObject()));
                    Model.getInstance().addServerMessage(user.getName() + " joined the chat");
                }
            }
        });

    }
}
