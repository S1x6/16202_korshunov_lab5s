package handler;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import model.Model;
import model.User;
import model.json.SharedUserObject;
import model.json.UserListObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserListHandler extends RequestHandler {

    @Override
    public void performAction(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((ex, data) -> {

            Optional<User> oUser = getAuthorizedUser(exchange);

            if (oUser.isPresent()) {
                oUser.get().updateTimeActive();
                List<SharedUserObject> users = Model.getInstance().getUsers().stream()
                        .map((user) -> new SharedUserObject(user.getName(), user.getId(), user.isOnline()))
                        .collect(Collectors.toList());
                exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
                exchange.getResponseSender().send(gson.toJson(new UserListObject(users)));
                return;
            }

            exchange.setStatusCode(403);
            exchange.getResponseSender().send("");
        });
    }
}
