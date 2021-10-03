package server.routes;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import static server.model.DataParser.parseData;

public class DataController {
    private DataController() {}

    public static Router getRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        router.get("/api/v1/covid-data").produces("application/json").handler(DataController::sendData);
        return router;
    }

    public static void sendData(RoutingContext ctx) {
        var response = ctx.response();
        response.putHeader("content-type", "application/json");
        parseData("/12-31-2020.csv").ifPresentOrElse(data -> {
            var jsonData = Json.encode(data);
            response.setStatusCode(200);
            response.end(jsonData);
        }, () -> {
            response.setStatusCode(500);
            var err = new JsonObject().put("error", "Error reading COVID data");
            response.end(err.encode());
        });
    }
}
