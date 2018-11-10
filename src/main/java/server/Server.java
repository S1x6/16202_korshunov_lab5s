package server;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import route.HttpRouter;

public class Server {

    private Undertow server;

    public Server() {
        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(asyncHandler).build();
    }

    public void start() {
        server.start();
    }

    private HttpHandler asyncHandler = exchange -> (
            new HttpRouter()).getHandler(
            exchange.getRelativePath(),
            exchange.getRequestMethod()).performAction(exchange);


    /*private HttpHandler handler = exchange -> {
        if (exchange.isInIoThread()) {
            exchange.dispatch(asyncHandler);
        }

    };*/


}
