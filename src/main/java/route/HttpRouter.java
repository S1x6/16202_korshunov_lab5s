package route;

import handler.LoginHandler;
import handler.LogoutHandler;
import handler.RequestHandler;
import handler.UserListHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import io.undertow.util.Methods;

public class HttpRouter {

    public RequestHandler getHandler(String path, HttpString method) {
        String[] rooms = path.split("/");
        RequestHandler handler = defaultHandler;
        if (rooms[1].equals("login") && method.equals(Methods.POST)) {
            handler = new LoginHandler();
        } else if (rooms[1].equals("logout") && method.equals(Methods.POST)) {
            handler = new LogoutHandler();
        } else if (rooms[1].equals("users") && method.equals(Methods.GET)) {
            handler = new UserListHandler();
        }
        return handler;
    }

    private RequestHandler defaultHandler = new RequestHandler() {
        @Override
        public void performAction(HttpServerExchange exchange) {
            exchange.setStatusCode(501);
            exchange.getResponseSender().send("Not implemented");
        }
    };
}
