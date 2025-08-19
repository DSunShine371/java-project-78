package hexlet.code;

import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
public class App {
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static void main(String[] args) {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() {
        var app = Javalin.create(config ->
            config.bundledPlugins.enableDevLogging()
        );

        app.get("/", ctx -> ctx.result("Hello World"));

        return app;
    }
}
