package handler;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import model.Model;
import model.User;
import model.json.SharedUserObject;

import java.util.Optional;

public class UserInfoHandler extends RequestHandler {
    @Override
    public void performAction(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((ex, data) -> {

            Optional<User> oUser = getAuthorizedUser(exchange);

            if (oUser.isPresent()) {
                oUser.get().updateTimeActive();
                exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
                Optional<User> oDesiredUser = Model.getInstance().getUsers().stream()
                        .filter(user -> user.getId() == getId()).findFirst();
                if (oDesiredUser.isPresent()) {
                    exchange.getResponseSender().send(
                            gson.toJson(new SharedUserObject(
                                    oDesiredUser.get().getName(),
                                    oDesiredUser.get().getId(),
                                    oDesiredUser.get().isOnline()
                            )));
                    return;
                }
            }

            exchange.setStatusCode(403);
            exchange.getResponseSender().send("");
        });
    }
}
