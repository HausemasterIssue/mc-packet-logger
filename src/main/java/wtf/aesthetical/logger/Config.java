package wtf.aesthetical.logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static List<String> allowedClientPackets = new ArrayList<>();
    public static List<String> allowedServerPackets = new ArrayList<>();

    public static boolean clientPackets = true;
    public static boolean serverPackets = true;

    public static int bind = Keyboard.KEY_RCONTROL;
    public static boolean toggled = false;

    public static final Path GAME_DIRECTORY = Minecraft.getMinecraft().gameDir.toPath();
    public static final Path CONFIG_FILE = GAME_DIRECTORY.resolve("packet_logger_config.json");

    public static void loadConfig() {
        if (!Files.exists(CONFIG_FILE)) {
            createConfig();
        }

        JsonObject json = defaultConfig();

        String contents = read(CONFIG_FILE);
        if (contents != null) {
            json = new Gson().fromJson(contents, JsonObject.class);
        }

        if (json.has("clientPackets")) {
            clientPackets = json.get("clientPackets").getAsBoolean();
        }

        if (json.has("serverPackets")) {
            serverPackets = json.get("serverPackets").getAsBoolean();
        }

        if (json.has("allowedClientPackets")) {
            JsonArray array = json.getAsJsonArray("allowedClientPackets");
            for (JsonElement element : array) {
                String name;
                if (element.isJsonPrimitive() && (name = element.getAsString()) != null) {
                    allowedClientPackets.add(name);
                }
            }
        }

        if (json.has("allowedServerPackets")) {
            JsonArray array = json.getAsJsonArray("allowedServerPackets");
            for (JsonElement element : array) {
                String name;
                if (element.isJsonPrimitive() && (name = element.getAsString()) != null) {
                    allowedServerPackets.add(name);
                }
            }
        }

        if (json.has("bind")) {
            bind = json.get("bind").getAsInt();
        }

        LogManager.getLogger("Config").info("Loaded config.");
    }

    public static String read(Path path) {
        try {
            return String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            return null;
        }
    }

    private static JsonObject defaultConfig() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("clientPackets", true);
        jsonObject.addProperty("serverPackets", true);
        jsonObject.add("allowedClientPackets", new JsonArray());
        jsonObject.add("allowedServerPackets", new JsonArray());
        jsonObject.addProperty("bind", Keyboard.KEY_RCONTROL);

        return jsonObject;
    }

    private static void createConfig() {
        JsonObject jsonObject = defaultConfig();

        try {
            Files.createFile(CONFIG_FILE);

            OutputStream stream = null;
            try {
                stream = new FileOutputStream(CONFIG_FILE.toFile());
                stream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
