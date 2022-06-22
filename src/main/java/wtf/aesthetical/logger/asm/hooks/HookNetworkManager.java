package wtf.aesthetical.logger.asm.hooks;

import net.minecraft.network.Packet;
import wtf.aesthetical.logger.Config;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HookNetworkManager {
    public static void logPacket(Packet<?> packet, PacketType packetType) {
        if (!Config.toggled) {
            return;
        }

        String packetName = packet.getClass().getSimpleName();
        if (packetType.equals(PacketType.CLIENT)) {
            if (!Config.clientPackets) {
                return;
            }

            if (!Config.allowedClientPackets.isEmpty() && !Config.allowedClientPackets.contains(packetName)) {
                return;
            }
        } else if (packetType.equals(PacketType.SERVER)) {
            if (!Config.serverPackets) {
                return;
            }

            if (!Config.allowedServerPackets.isEmpty() && !Config.allowedServerPackets.contains(packetName)) {
                return;
            }
        }

        Map<String, Object> fields = getFields(packet, packet.getClass());

        StringBuilder builder = new StringBuilder();
        if (!fields.isEmpty()) {
            for (Entry<String, Object> entry : fields.entrySet()) {
                builder.append(entry.getKey()).append(":").append(" ").append(entry.getValue()).append("\n");
            }
        }

        System.out.println(packetName + ":\n" + builder);
    }

    private static Map<String, Object> getFields(Object parent, Class<?> clazz) {
        Map<String, Object> fields = new LinkedHashMap<>();

        while (clazz.getSuperclass() != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                Object value = null;
                try {
                    value = field.get(parent);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                fields.put(field.getName(), value);
            }

            clazz = clazz.getSuperclass();
        }

        return fields;
    }

    public enum PacketType {
        CLIENT, SERVER
    }
}
