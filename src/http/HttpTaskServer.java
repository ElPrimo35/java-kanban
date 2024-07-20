package http;

import adapters.DurationAdapter;
import adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import managers.InMemoryTaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    InMemoryTaskManager inMemoryTaskManager;
    Gson gson = getGson();
    HttpServer httpServer = HttpServer.create();

    public HttpTaskServer(InMemoryTaskManager inMemoryTaskManager) throws IOException {
        this.inMemoryTaskManager = inMemoryTaskManager;
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer(new InMemoryTaskManager());
        httpTaskServer.start();
    }

    public void start() throws IOException {
        httpServer.bind(new InetSocketAddress(9090), 0);
        httpServer.createContext("/tasks", new TaskHandler(inMemoryTaskManager, gson));
        httpServer.createContext("/subtasks", new SubtasksHandler(inMemoryTaskManager, gson));
        httpServer.createContext("/epics", new EpicsHandler(inMemoryTaskManager, gson));
        httpServer.createContext("/history", new HistoryHandler(inMemoryTaskManager, gson));
        httpServer.createContext("/prioritized", new PrioritizedHandler(inMemoryTaskManager, gson));
        httpServer.start();
        System.out.println("Сервер запущен");
    }

    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
    }

    public void stop() {
        httpServer.stop(0);
    }
}
