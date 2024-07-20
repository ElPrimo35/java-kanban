package httpServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import responsers.DeleteResponse;
import responsers.ErrorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {
    protected final InMemoryTaskManager inMemoryTaskManager;
    protected final Gson gson;

    public BaseHttpHandler(InMemoryTaskManager inMemoryTaskManager, Gson gson) {
        this.inMemoryTaskManager = inMemoryTaskManager;
        this.gson = gson;
    }

    protected void sendText(HttpExchange h, Object body, int code) throws IOException {
        String response = gson.toJson(body);
        byte[] resp = response.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(code, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("Объект не найден");
        String responseJson = gson.toJson(errorResponse);
        byte[] resp = responseJson.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(404, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendHasInteractions(HttpExchange h) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("Задачи пересекаются");
        String responseJson = gson.toJson(errorResponse);
        byte[] resp = responseJson.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(406, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendDeleted(HttpExchange h) throws IOException {
        DeleteResponse deleteResponse = new DeleteResponse("Задача успешно удалена");
        String responseJson = gson.toJson(deleteResponse);
        byte[] resp = responseJson.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }
}
