package httpServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {
    public HistoryHandler(InMemoryTaskManager inMemoryTaskManager, Gson gson) {
        super(inMemoryTaskManager, gson);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        sendText(httpExchange, inMemoryTaskManager.getHistory(), 200);
    }
}
