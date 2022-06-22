package wtf.aesthetical.logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = PacketLogger.NAME, modid = PacketLogger.MOD_ID, version = PacketLogger.VERSION)
public class PacketLogger {
    public static final String NAME = "PacketLogger";
    public static final String MOD_ID = "packet_logger";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Config.loadConfig();
    }
}
