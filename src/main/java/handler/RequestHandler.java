package handler;

import com.google.gson.Gson;
import io.undertow.server.HttpServerExchange;
import model.Model;
import model.User;

import java.util.Optional;

public abstract class RequestHandler {

    private int id;
    protected Gson gson = new Gson();

    public abstract void performAction(HttpServerExchange exchange);

    protected Optional<User> getAuthorizedUser(HttpServerExchange exchange) {
        if (exchange.getRequestHeaders().contains("Authorization")) {
            String token = exchange.getRequestHeaders().get("Authorization").getFirst();
            return Model.getInstance().getUsers().stream()
                    .filter(user -> user.getToken().equals(token)).findFirst();
        }
        return Optional.empty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
