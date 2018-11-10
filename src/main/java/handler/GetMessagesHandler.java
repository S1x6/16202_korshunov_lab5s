package handler;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import model.Model;
import model.User;
import model.json.MessageList;

import java.util.Deque;
import java.util.Optional;

public class GetMessagesHandler extends RequestHandler {
    @Override
    public void performAction(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((ex, data) -> {

            Optional<User> oUser = getAuthorizedUser(exchange);

            if (oUser.isPresent()) {
                int offset, count;
                try {
                    Deque<String> stringDeque = exchange.getQueryParameters().get("offset");
                    String sOffset = stringDeque != null ? stringDeque.getFirst() : "0";
                    offset = Integer.valueOf(sOffset);
                } catch (NumberFormatException exc) {
                    offset = 0;
                }
                try {
                    Deque<String> stringDeque = exchange.getQueryParameters().get("count");
                    String sCount = stringDeque != null ? stringDeque.getFirst() : "10";
                    count = Integer.valueOf(sCount);
                } catch (NumberFormatException exc) {
                    count = 10;
                }
                if (count > 100) {
                    count = 100;
                }
                exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
                exchange.getResponseSender().send(gson.toJson(new MessageList(Model.getInstance().getMessages(offset, count)),
                        MessageList.class));
            } else {
                exchange.setStatusCode(403);
                exchange.getResponseSender().send("");
            }
        });
    }
}
