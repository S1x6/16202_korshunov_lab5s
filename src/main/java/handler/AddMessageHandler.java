package handler;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import model.Message;
import model.Model;
import model.User;
import model.json.MessageObject;
import model.json.NewMessageObject;

import java.util.Optional;

public class AddMessageHandler extends RequestHandler {
    @Override
    public void performAction(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((ex, data) -> {

            Optional<User> oUser = getAuthorizedUser(exchange);

            if (oUser.isPresent()) {
                oUser.get().updateTimeActive();
                NewMessageObject messageObject = gson.fromJson(new String(data), NewMessageObject.class);
                Message msg = Model.getInstance().addMessage(messageObject.getMessage(), oUser.get().getId(), oUser.get().getName());
                exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
                exchange.getResponseSender().send(gson.toJson(new MessageObject(
                        msg.getId(),
                        msg.getText(),
                        msg.getAuthorId()
                )));
            } else {
                exchange.setStatusCode(403);
                exchange.getResponseSender().send("");
            }
        });
    }
}