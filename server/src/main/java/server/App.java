package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import server.routes.DataController;

public class App extends AbstractVerticle {
    final int PORT = 8080;

    @Override public void start() {
        System.out.println("servidor corriendo en el puerto " + PORT);
        Router router = DataController.getRouter(vertx);
        vertx.createHttpServer().requestHandler(router).listen(PORT);
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new App());
    }
}
