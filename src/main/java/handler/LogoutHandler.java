package handler;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import model.Model;
import model.User;
import model.json.ByeObject;

import java.util.Optional;


public class LogoutHandler extends RequestHandler {
    @Override
    public void performAction(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((ex, data) -> {

            Optional<User> oUser = getAuthorizedUser(exchange);

            if (oUser.isPresent()) {
                Model.getInstance().getUsers().remove(oUser.get());
                exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
                exchange.getResponseSender().send(gson.toJson(new ByeObject("bye!")));
                return;
            }

            exchange.setStatusCode(403);
            exchange.getResponseSender().send("");
        });
    }
}
