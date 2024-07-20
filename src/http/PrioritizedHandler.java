package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {
    public PrioritizedHandler(InMemoryTaskManager inMemoryTaskManager, Gson gson) {
        super(inMemoryTaskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!httpExchange.getRequestMethod().equals("GET")) {
            sendWrongMethod(httpExchange);
        }
        sendText(httpExchange, inMemoryTaskManager.getPrioritizedTasks(), 200);
    }
}
