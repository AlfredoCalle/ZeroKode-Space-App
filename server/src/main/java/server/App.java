package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class App extends AbstractVerticle {
    final int PORT = 8080;

    @Override public void start() {
        System.out.println("servidor corriendo en el puerto " + PORT);
        vertx.createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(PORT);
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new App());
    }
}
