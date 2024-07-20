package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;
import models.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler {
    public EpicsHandler(InMemoryTaskManager inMemoryTaskManager, Gson gson) {
        super(inMemoryTaskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        switch (httpExchange.getRequestMethod()) {
            case "POST":
                String path = httpExchange.getRequestURI().getPath();
                String requestBody = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                if (requestBody.isEmpty()) {
                    sendBadRequest(httpExchange);
                }
                Epic newEpic = gson.fromJson(requestBody, Epic.class);
                if (path.split("/").length == 3) {
                    inMemoryTaskManager.updateEpic(newEpic);
                    sendText(httpExchange, inMemoryTaskManager.getEpicById(newEpic.getId()), 201);
                    break;
                }
                int epicId = inMemoryTaskManager.createEpic(newEpic);
                sendText(httpExchange, inMemoryTaskManager.getEpicById(epicId), 201);
                break;
            case "GET":
                String path1 = httpExchange.getRequestURI().getPath();
                if (path1.split("/").length == 3) {
                    int id = Integer.parseInt(path1.split("/")[2]);
                    if (inMemoryTaskManager.getEpicById(id) == null) {
                        sendNotFound(httpExchange);
                        break;
                    }
                    Epic epic = inMemoryTaskManager.getEpicById(id);
                    sendText(httpExchange, epic, 200);
                    break;
                }
                if (path1.split("/").length == 4) {
                    int id = Integer.parseInt(path1.split("/")[2]);
                    if (inMemoryTaskManager.getEpicById(id) == null) {
                        sendNotFound(httpExchange);
                        break;
                    }
                    sendText(httpExchange, inMemoryTaskManager.getEpicById(id).getSubtaskIds(), 200);
                }
                sendText(httpExchange, inMemoryTaskManager.getEpicList(), 200);
                break;
            case "DELETE":
                String path2 = httpExchange.getRequestURI().getPath();
                int id1 = Integer.parseInt(path2.split("/")[2]);
                inMemoryTaskManager.deleteEpicById(id1);
                sendDeleted(httpExchange);
        }
    }
}
