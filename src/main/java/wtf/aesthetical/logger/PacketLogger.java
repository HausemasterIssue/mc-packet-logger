package wtf.aesthetical.logger;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

@Mod(name = PacketLogger.NAME, modid = PacketLogger.MOD_ID, version = PacketLogger.VERSION)
public class PacketLogger {
    public static final String NAME = "PacketLogger";
    public static final String MOD_ID = "packet_logger";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Config.loadConfig();

        // register to forge event bus
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        int keyCode = Keyboard.getEventKey();
        if (keyCode != Keyboard.KEY_NONE
                && keyCode == Config.bind
                && !Keyboard.getEventKeyState()
                && Minecraft.getMinecraft().currentScreen == null) {

            Config.toggled = !Config.toggled;
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(
                    new TextComponentString(ChatFormatting.RED + "[PacketLogger] " + ChatFormatting.RESET + Config.toggled),
                    -133769420
            );
        }
    }
}
