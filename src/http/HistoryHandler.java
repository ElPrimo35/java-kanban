package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {
    public HistoryHandler(InMemoryTaskManager inMemoryTaskManager, Gson gson) {
        super(inMemoryTaskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!httpExchange.getRequestMethod().equals("GET")) {
            sendWrongMethod(httpExchange);
        }
        sendText(httpExchange, inMemoryTaskManager.getHistory(), 200);
    }
}
